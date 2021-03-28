package com.jake.sarate.definitelynotconnect4.controllers

import com.jake.sarate.definitelynotconnect4.models.CreateGameResponse
import com.jake.sarate.definitelynotconnect4.models.GameInstance
import com.jake.sarate.definitelynotconnect4.models.GameRequest
import com.jake.sarate.definitelynotconnect4.models.GetGameResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTests {

    @LocalServerPort
    var port: Int? = null

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun creatingAGameShouldReturn200() {
        val request = HttpEntity(GameRequest(arrayOf("player 1", "player 2"), 4, 4))
        val response = restTemplate.exchange("http://localhost:$port/api/drop_token", HttpMethod.POST , request, CreateGameResponse::class.java)
        val body = response.body
        println(body)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(body)
    }
}