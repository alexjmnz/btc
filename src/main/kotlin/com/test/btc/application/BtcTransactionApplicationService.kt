package com.test.btc.application

import com.test.btc.domain.BtcTransaction
import com.test.btc.domain.BtcTransactionRepository
import com.test.btc.domain.read.BtcTransactionView
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Service
class BtcTransactionApplicationService(val repository: BtcTransactionRepository) {


    fun register(command: CreateBtcTransactionCommand) = repository.insert(BtcTransaction(command))

    fun findTransactions(query: FindHistoryQuery): Flux<BtcTransactionView> {
        val startDatetime = toLocalDatetime(query.startDatetime)
        val endDatetime = toLocalDatetime(query.endDatetime)
        return repository.findTransactions(startDatetime, endDatetime).map {
            it.datetime = it.datetime.truncatedTo(ChronoUnit.HOURS)
            it
        }/*TODO It would be better to have this logic implemented in the view to simplify service.
            It might reduce the fetched data.*/
                .groupBy { it.datetime }
                .flatMap { group -> group.last().map { BtcTransactionView(group.key()!!, it.amount) }}
    }

    private fun toLocalDatetime (datetime: ZonedDateTime) = LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault())

}