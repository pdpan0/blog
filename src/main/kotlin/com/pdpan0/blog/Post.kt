package com.pdpan0.blog

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,
        val title: String,
        val description: String,
        val body: String,
        val createdAt: LocalDate,
        val updatedAt: LocalDate
)