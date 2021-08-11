package com.pdpan0.blog.dto

class ErrorMessageDTO(
        val status: Int,
        val type: String,
        val message: String,
        val objectId: Int?
)