package com.example.framework.sources.network.models

data class Info (
    val count: Long,
    val pages: Long,
    val next: String?,
    val prev: Any? = null
)