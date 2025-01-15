package com.tribe7.statecraft.state

sealed class Result<T> {
    data class Success<T>(var data: T) : Result<T>()
    data class Error<T>(var throwable: Throwable) : Result<T>()
    data class Progress<T>(var percentage: Int) : Result<T>()
    class Unauthorized<T>(val message: String? = "Unauthorized Access") : Result<T>()
    class Empty<T>(val message: String? = null): Result<T>()
    class Initial<T>(): Result<T>()
}
