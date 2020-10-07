package com.test.btc.resource

import com.test.btc.adapter.persistence.ReactiveBtcTransactionRepository
import com.test.btc.application.BtcTransactionApplicationService
import com.test.btc.application.CreateBtcTransactionCommand
import com.test.btc.configuration.TestConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.ZonedDateTime

@WebFluxTest
@ContextConfiguration(
        classes = [
            TestConfiguration::class,
            BtcTransactionApplicationService::class,
            ReactiveBtcTransactionRepository::class,
            BtcResource::class
        ])
class IntegrationTest(@Autowired val client: WebTestClient) {

    @Test
    fun testRegisterTransactionSuccess() {
        client.post()
                .uri("/api/v1/btc/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CreateBtcTransactionCommand(ZonedDateTime.now(), 2.0))
                .exchange()
                .expectStatus()
                .isCreated
                .expectBody()
                .jsonPath("amount")
                .isEqualTo(2.0)
    }

    @Test
    fun testFindTransactionSuccess() {
        client.get()
                .uri("/api/v1/btc/transactions?startDatetime={start}&endDatetime={end}",
                        "2020-10-04T15:00:00+01:00", "2020-10-06T15:00:00+02:00")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("[0].amount")
                .isNumber
    }

}