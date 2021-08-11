package com.pdpan0.blog.dto

class GetResponseBodyDTO<T>(
    val totalRows: Int?,
    val response: T?
)