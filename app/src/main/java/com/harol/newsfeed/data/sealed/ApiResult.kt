package com.harol.newsfeed.data.sealed

// Used Generics for this Utility
//
sealed class ApiResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {

    class Success<T>(
        data: T
    ) : ApiResult<T>(
        data = data
    )

    class Error<T>(
        errorMessage: String,
    ) : ApiResult<T>(
        errorMessage = errorMessage
    )
}

