package com.pdpan0.blog

import com.fasterxml.jackson.core.JsonEncoding
import com.pdpan0.blog.dto.ErrorMessageDTO
import com.pdpan0.blog.dto.GetResponseBodyDTO
import com.pdpan0.blog.dto.ObjectIdDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDate

@RestController
@RequestMapping("v1/posts", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostController {

    @Autowired
    lateinit var repository: PostRepository

    @GetMapping
    fun getPosts(@RequestParam startDate: String, @RequestParam endDate: String): ResponseEntity<Any> {
        var posts: List<Post>

        try {
            posts = repository.findByCreatedAtBetween(LocalDate.parse(startDate), LocalDate.parse(endDate))
        } catch (error: Exception) {
            return ResponseEntity.internalServerError()
                .body(
                    Json.encodeToString(ErrorMessageDTO(500,
                    error.javaClass.simpleName,
                    "Não foi possível realizar esta operação."
                )))
        }

        return if (posts.isEmpty()) ResponseEntity.noContent().build()
            else ResponseEntity.ok(GetResponseBodyDTO(posts.count(),posts))
    }

    @PostMapping
    fun createPost(@RequestBody(required = true) post: Post): ResponseEntity<Any> {
        return try {
            post.id = null
            ResponseEntity.created(URI.create("")).body(ObjectIdDTO(repository.save(post).id))
        } catch (error: Exception) {
            ResponseEntity.internalServerError()
                .body(
                    Json.encodeToString(ErrorMessageDTO(500,
                        error.javaClass.simpleName,
                        "Não foi possível realizar esta operação."
                    ))
            )
        }
    }

    @PutMapping("/{postId}")
    fun updatePost(@PathVariable("postId") postId: Int, @RequestBody(required = true) post: Post): ResponseEntity<Any> {
        return try {
            if (repository.existsById(postId)) {
                post.id = postId
                repository.save(post)
                ResponseEntity.ok().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (error: Exception) {
            ResponseEntity.internalServerError()
                .body(
                    Json.encodeToString(ErrorMessageDTO(500,
                        error.javaClass.simpleName,
                        "Não foi possível realizar esta operação."
                    ))
            )
        }
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable("postId") postId: Int): ResponseEntity<Any> {
        return try {
            if (repository.existsById(postId)) {
                repository.deleteById(postId)
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (error: Exception) {
            ResponseEntity.internalServerError()
                .body(
                    Json.encodeToString(ErrorMessageDTO(500,
                        error.javaClass.simpleName,
                        "Não foi possível realizar esta operação."
                    ))
            )
        }
    }
}