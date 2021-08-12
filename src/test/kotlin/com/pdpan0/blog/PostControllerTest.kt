package com.pdpan0.blog

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
internal class PostControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var  postRepository: PostRepository

    var mockPost: Post = Post(
        null,
        "Javeiros Profissionais",
        "Segredo para se tornar um.",
        "Odeie C#",
        LocalDate.of(2021,8,1),
        LocalDate.of(2021,8,1)
    )

    @BeforeEach
    fun setUp() {
        //TODO: Algo antes de rodar o teste.
    }

    @Test
    fun `test find all`() {
        postRepository.save(mockPost)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/v1/posts")
                .queryParam("startDate", "2021-08-01")
                .queryParam("endDate", "2021-08-30"))
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andDo(MockMvcResultHandlers.print())

        postRepository.deleteAll()

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/v1/posts")
                .queryParam("startDate", "2021-08-01")
                .queryParam("endDate", "2021-08-30"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test save post`() {
        val json = ObjectMapper()
            .findAndRegisterModules()
            .writeValueAsString(mockPost)

        postRepository.deleteAll()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/posts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated)
        .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test delete post`() {
        val postId = postRepository.save(mockPost).id

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/v1/posts/${postId}"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())

        val findById = postRepository.existsById(postId!!)

        Assertions.assertFalse(findById)
    }

    @Test
    fun `test update post`() {

    }
}