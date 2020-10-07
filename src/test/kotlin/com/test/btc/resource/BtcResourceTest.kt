package com.test.btc.resource

import com.test.btc.application.BtcTransactionApplicationService
import com.test.btc.application.CreateBtcTransactionCommand
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.ZonedDateTime

@WebFluxTest(controllers = [BtcResource::class])
@ContextConfiguration(
        classes = [
            BtcResource::class,
            ResourceExceptionHandler::class
        ])
class BtcResourceTest(@Autowired val client: WebTestClient) {

    @MockBean
    lateinit var applicationService: BtcTransactionApplicationService

    @Test
    fun testCommandValidation() {
        client.post()
                .uri("/api/v1/btc/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus()
                .isBadRequest
    }

    @ParameterizedTest
    @ValueSource(doubles = [-23.0, 0.0])
    fun testPositiveAmountValidation(amount: Double) {
        client.post()
                .uri("/api/v1/btc/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CreateBtcTransactionCommand(ZonedDateTime.now(), amount))
                .exchange()
                .expectStatus()
                .isBadRequest
                .expectBody()
                .jsonPath("$.errors[0]")
                .isEqualTo("amount must be a positive number, zero is invalid")
    }

    @Test
    fun testDateParsingErrorValidation() {
        client.post()
                .uri("/api/v1/btc/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"datetime\":\"1987-09-23\"}")
                .exchange()
                .expectStatus()
                .isBadRequest
    }

    @Test
    fun testQueryTransactionsFail() {
        client.get()
                .uri("/api/v1/btc/transactions")
                .exchange()
                .expectStatus()
                .isBadRequest
    }

    @Test
    fun testQueryTransactionsInvalidDate() {
        client.get()
                .uri("/api/v1/btc/transactions?startDatetime={start}",
                        "2020-10-04T12:12:12+01:00")
                .exchange()
                .expectStatus()
                .isBadRequest
    }
}