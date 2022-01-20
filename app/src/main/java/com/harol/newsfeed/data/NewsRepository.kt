package com.harol.newsfeed.data

import com.harol.newsfeed.data.sealed.ApiResult
import com.harol.newsfeed.models.api.NewsResponse
import com.harol.newsfeed.network.NewsApiService

class NewsRepository(
    private val newsApiService: NewsApiService
) {

    suspend fun getTopNews(): ApiResult<NewsResponse> {
        val response = try {
            newsApiService.getTopNews("us")
        } catch (e: Exception) {
            return ApiResult.Error(e.localizedMessage ?: "")
        }
        return ApiResult.Success(data = response)
    }

    suspend fun getNewsByCategory(category: String): ApiResult<NewsResponse> {
        val response = try {
            newsApiService.getNewsByCategory(category)
        } catch (e: Exception) {
            return ApiResult.Error(e.localizedMessage ?: "")
        }
        return ApiResult.Success(data = response)
    }

    suspend fun getNewsBySource(source: String): ApiResult<NewsResponse> {
        val response = try {
            newsApiService.getNewsBySource(source)
        } catch (e: Exception) {
            return ApiResult.Error(e.localizedMessage ?: "")
        }
        return ApiResult.Success(data = response)
    }


    suspend fun getNewsByKeyword(query: String): ApiResult<NewsResponse> {
        val response = try {
            newsApiService.getNewsByKeyword(query)
        } catch (e: Exception) {
            return ApiResult.Error(e.localizedMessage ?: "")
        }
        return ApiResult.Success(data = response)
    }
}