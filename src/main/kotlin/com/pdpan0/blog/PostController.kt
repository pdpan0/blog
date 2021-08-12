package com.pdpan0.blog

import com.pdpan0.blog.dto.ErrorMessageDTO
import com.pdpan0.blog.dto.GetResponseBodyDTO
import com.pdpan0.blog.dto.ObjectIdDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDate

@RestController
class PostController {

    @Autowired
    lateinit var repository: PostRepository

    @GetMapping
    fun getPosts(@RequestParam startDate: String, @RequestParam endDate: String): ResponseEntity<GetResponseBodyDTO> {
        var posts: List<Post>

        try {
            posts = repository.findByCreatedAtBetween(LocalDate.parse(startDate), LocalDate.parse(endDate))
        } catch (error: Exception) {
            return ResponseEntity.internalServerError().body(GetResponseBodyDTO(null,error.message))
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
            ResponseEntity.internalServerError().body(error)
        }
    }

    @PutMapping("/{postId}")
    fun updatePost(@PathVariable("postId") postId: Int, @RequestBody(required = true) post: Post): ResponseEntity<GetResponseBodyDTO> {
        return try {
            if (repository.existsById(postId)) {
                post.id = postId
                repository.save(post)
                ResponseEntity.ok().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (error: Exception) {
            ResponseEntity.internalServerError().body(GetResponseBodyDTO(
                null,error.message
            ))
        }
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable("postId") postId: Int): ResponseEntity<GetResponseBodyDTO> {
        return try {
            if (repository.existsById(postId)) {
                repository.deleteById(postId)
                ResponseEntity.noContent().build()
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (error: Exception) {
            ResponseEntity.internalServerError().body(GetResponseBodyDTO(
                null,
                error.message
            ))
        }
    }
}