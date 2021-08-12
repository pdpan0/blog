package com.pdpan0.blog.dto

import kotlinx.serialization.Serializable

@Serializable
class ErrorMessageDTO(
        val status: Int,
        val type: String,
        val message: String,
        val objectId: Int? = null
)