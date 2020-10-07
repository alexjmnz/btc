package com.test.btc.application

import com.fasterxml.jackson.annotation.JsonFormat
import com.test.btc.Constants
import java.time.ZonedDateTime
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class CreateBtcTransactionCommand(
        @field:NotNull(message = "datetime cannot be null")
        @JsonFormat(pattern = Constants.DATE_TIME_PATTERN)
        val datetime: ZonedDateTime,
        @field:Positive(message = "amount must be a positive number, zero is invalid") val amount: Double
)