package com.harol.newsfeed.data

import com.harol.newsfeed.data.sealed.ApiResult
import com.harol.newsfeed.models.api.NewsResponse
import com.harol.newsfeed.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsApiService: NewsApiService
) {

    suspend fun getTopNews(): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            val result = try {
                val data = newsApiService.getTopNews("us")
                ApiResult.Success(data = data)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "")
            }
            result
        }
    }

    suspend fun getNewsByCategory(category: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            val result = try {
                val data = newsApiService.getNewsByCategory(category)
                ApiResult.Success(data = data)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "")
            }
            result
        }
    }

    suspend fun getNewsBySource(source: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            val result = try {
                val data = newsApiService.getNewsBySource(source)
                ApiResult.Success(data = data)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "")
            }
            result
        }
    }


    suspend fun getNewsByKeyword(query: String): ApiResult<NewsResponse> {
        return withContext(Dispatchers.IO) {
            val result = try {
                val data = newsApiService.getNewsByKeyword(query)
                ApiResult.Success(data = data)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "")
            }
            result
        }
    }
}