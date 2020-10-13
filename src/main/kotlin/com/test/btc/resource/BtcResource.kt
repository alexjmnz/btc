package com.test.btc.resource

import com.test.btc.application.BtcTransactionApplicationService
import com.test.btc.application.CreateBtcTransactionCommand
import com.test.btc.application.FindHistoryQuery
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/btc/transactions")
class BtcResource(val service: BtcTransactionApplicationService) {

    @PostMapping
    fun save(@RequestBody @Valid command: CreateBtcTransactionCommand, serverWebExchange: ServerWebExchange) =
            service.register(command)
                    .doOnSuccess{serverWebExchange.response.statusCode = HttpStatus.CREATED}

    @GetMapping(produces = [
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_STREAM_JSON_VALUE
    ])
    fun findTransactions(@Valid query: FindHistoryQuery) = service.findTransactions(query)
}