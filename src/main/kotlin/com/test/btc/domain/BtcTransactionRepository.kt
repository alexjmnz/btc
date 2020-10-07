package com.test.btc.domain

import com.test.btc.application.FindTransactionsQuery
import com.test.btc.domain.read.BtcTransactionView
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BtcTransactionRepository {

    fun insert(transaction: BtcTransaction): Mono<BtcTransaction>

    fun findTransactions(command: FindTransactionsQuery): Flux<BtcTransactionView>
}