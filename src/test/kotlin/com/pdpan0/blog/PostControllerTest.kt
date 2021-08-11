package com.pdpan0.blog

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.MultiValueMap
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

        mockMvc.perform(MockMvcRequestBuilders
                .get("/")
                .queryParam("startDate", "2021-08-01")
                .queryParam("endDate", "2021-08-30")
        ).andExpect(MockMvcResultMatchers.status().isOk)
         .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test no content`() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/")
            .queryParam("startDate", "2021-08-01")
            .queryParam("endDate", "2021-08-30")
        ).andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }
}