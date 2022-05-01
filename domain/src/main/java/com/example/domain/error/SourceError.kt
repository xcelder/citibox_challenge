package com.example.domain.error

sealed interface SourceError

object DDBBError : SourceError
object NetworkError : SourceError