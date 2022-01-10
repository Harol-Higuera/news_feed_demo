package com.harol.newsfeed.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class CustomLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        Log.d("Harol", "::::::::::::::: S T A R T :::::::::::::")

        Log.d("Harol", "⬆️ 🔥🔥🔥🔥 Request Path:\n${request.url}")
        Log.d("Harol", "⬆️ 🔥🔥🔥🔥 Request Method:\n${request.method}")

        val headers = request.headers
        if (headers.size > 0) {
            Log.d("Harol", "⬆️ 🔥🔥🔥🔥 Request Headers:\n$headers")
        }

        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Log.e("Harol", " HTTP FAILED: $e")
            throw e
        }

        Log.d("Harol", "⬇️ 🔥🔥🔥🔥 Response Code:\n${response.code}")

        response.body?.let { responseBody ->

            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer

            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val contentType = responseBody.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            try {
                val json = JSONObject(buffer.clone().readString(charset)).toString(2)

                Log.d("Harol", "⬇️ 🔥🔥🔥🔥 Response Body:\n$json")

            } catch (e: Exception) {
                Log.e("Harol", "Cannot print BODY: $e")
                throw e
            }

        }

        Log.d("Harol", "::::::::::::::: E N D :::::::::::::")

        return response
    }

}