package com.test.btc.adapter.persistence

import com.test.btc.application.FindTransactionsQuery
import com.test.btc.domain.BtcTransaction
import com.test.btc.domain.BtcTransactionRepository
import com.test.btc.domain.read.BtcTransactionView
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneId

@Repository
class ReactiveBtcTransactionRepository(val databaseClient: DatabaseClient) : BtcTransactionRepository {

    override fun insert(transaction: BtcTransaction) =
            databaseClient.insert()
                    .into(transaction.javaClass)
                    .using(transaction)
                    .map { row ->
                        transaction.id = row.get("id") as Long
                        transaction
                    }
                    .first()

    override fun findTransactions(command: FindTransactionsQuery) : Flux<BtcTransactionView> {
        val startDatetime = LocalDateTime.ofInstant(command.startDatetime.toInstant(), ZoneId.systemDefault())
        val endDatetime = LocalDateTime.ofInstant(command.endDatetime.toInstant(), ZoneId.systemDefault())
        return databaseClient.select()
                .from(BtcTransactionView::class.java)
                .matching(Criteria.where("datetime").between(startDatetime, endDatetime))
                .orderBy(Sort.by("datetime"))
                .fetch()
                .all()
    }
}