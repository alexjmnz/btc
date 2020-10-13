package com.test.btc.domain.read

import java.time.LocalDateTime

data class BtcTransactionView(var datetime: LocalDateTime, val amount: Double)