package com.test.btc.domain

import com.test.btc.domain.read.BtcTransactionView
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface BtcTransactionRepository {

    fun insert(transaction: BtcTransaction): Mono<BtcTransaction>

    fun findTransactions(startDatetime: LocalDateTime, endDatetime: LocalDateTime): Flux<BtcTransactionView>
}