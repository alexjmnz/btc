package com.test.btc.adapter.persistence

import com.test.btc.application.FindTransactionsQuery
import com.test.btc.domain.BtcTransaction
import com.test.btc.domain.BtcTransactionRepository
import com.test.btc.domain.read.BtcTransactionView
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository

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

    override fun findTransactions(command: FindTransactionsQuery) =
            databaseClient.select()
                    .from(BtcTransactionView::class.java)
                    .matching(Criteria.where("datetime").between(command.startDatetime, command.endDatetime))
                    .orderBy(Sort.by("datetime"))
                    .fetch()
                    .all()
}