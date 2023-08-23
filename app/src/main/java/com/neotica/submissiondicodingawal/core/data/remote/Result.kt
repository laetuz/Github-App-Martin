package com.neotica.submissiondicodingawal.core.data.remote

sealed class Result<R>(val data: R? = null, val message: String? = null) {
    class Loading<T>: Result<T>()
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String?) : Result<T>(message = message)
}