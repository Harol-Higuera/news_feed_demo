package com.therapeutic.app.data.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


class CustomLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()


        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Log.e("ApiLogger", " HTTP FAILED: $e")
            throw e
        }

        Log.d("ApiLogger", "::::::::::::::: S T A R T :::::::::::::")

        Log.d("ApiLogger", "⬆️ 🔥🔥🔥🔥 Request Path:\n${request.url}")
        Log.d("ApiLogger", "⬆️ 🔥🔥🔥🔥 Request Method:\n${request.method}")

        val headers = request.headers
        if (headers.size > 0) {
            Log.d("ApiLogger", "⬆️ 🔥🔥🔥🔥 Request Headers:\n$headers")
        }

        request.body?.let { body ->
            Log.d(
                "ApiLogger",
                "⬆️ 🔥🔥🔥🔥 Request Body:\n${getPrettyJson(bodyToString(body) ?: "")}"
            )
        }

        Log.d("ApiLogger", "⬆️ 🔥🔥🔥🔥 Request Path:\n${request.body}")

        Log.d("ApiLogger", "✅ 🔥🔥🔥🔥 Response Code:\n${response.code}")

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

            val string = buffer.clone().readString(charset)

            try {
                Log.d("ApiLogger", "✅ 🔥🔥🔥🔥 Response Body:\n${getPrettyJson(string)}")

            } catch (e: Exception) {
                e.message?.let { message ->
                    Log.e("ApiLogger", "E1. Cannot print BODY: $message")
                }
            }
        }
        Log.d("ApiLogger", "::::::::::::::: E N D :::::::::::::")
        return response
    }


    private fun getPrettyJson(string: String): String {
        val jsonParser = JsonParser()
        val gson = GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping()
            .create()
        val je: JsonElement = jsonParser.parse(string)
        return gson.toJson(je)
    }

    private fun bodyToString(request: RequestBody): String? {
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "Did not work"
        }
    }
}