package com.test.btc.domain.read

import java.time.LocalDateTime

data class BtcTransactionView(val datetime: LocalDateTime, val amount: Double, val balance: Double)