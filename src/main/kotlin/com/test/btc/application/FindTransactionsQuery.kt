package com.test.btc.application

import com.test.btc.Constants
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime
import javax.validation.constraints.NotNull

data class FindTransactionsQuery(
        @field:NotNull(message = "startDatetime cannot be null")
        @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN)
        val startDatetime: ZonedDateTime,
        @field:NotNull(message = "endDatetime cannot be null")
        @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN)
        val endDatetime: ZonedDateTime)