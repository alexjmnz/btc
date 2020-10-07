package com.test.btc.application

import com.test.btc.domain.BtcTransaction
import com.test.btc.domain.BtcTransactionRepository
import org.springframework.stereotype.Service

@Service
class BtcTransactionApplicationService(val repository: BtcTransactionRepository) {


    fun register(command: CreateBtcTransactionCommand)= repository.insert(BtcTransaction(command))

    fun findTransactions(command: FindTransactionsQuery) = repository.findTransactions(command)

}