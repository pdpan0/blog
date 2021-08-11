package com.pdpan0.blog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface PostRepository : JpaRepository<Post, Int> {

    fun findByCreatedAtBetween(startDate: LocalDate, endDate: LocalDate): List<Post>
}