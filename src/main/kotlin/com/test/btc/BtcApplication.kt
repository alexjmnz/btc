package com.test.btc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BtcApplication

object Constants {
	const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS]XXXXX"
}

fun main(args: Array<String>) {
	runApplication<BtcApplication>(*args)
}
