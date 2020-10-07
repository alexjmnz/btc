package com.test.btc.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "btc.database")
data class Database(val host: String,
                    val port: Int,
                    val name: String,
                    val username: String,
                    val password: String,
                    val schema: String)