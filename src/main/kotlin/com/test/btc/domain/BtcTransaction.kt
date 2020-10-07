package com.test.btc.domain

import com.test.btc.application.CreateBtcTransactionCommand
import org.springframework.data.annotation.Id
import java.time.ZonedDateTime

data class BtcTransaction(@Id var id: Long?, val datetime: ZonedDateTime, val amount: Double) {

    constructor(creationCommand: CreateBtcTransactionCommand) :
            this(null, creationCommand.datetime, creationCommand.amount)
}