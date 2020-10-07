package com.test.btc.configuration

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator
import org.springframework.data.r2dbc.core.DatabaseClient



@Configuration
class TestConfiguration {

    @Bean
    fun connectionFactory() = H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                    .url("mem:testdb;DB_CLOSE_DELAY=-1;")
                    .username("sa")
                    .build())

    @Bean
    fun databaseClient(connectionFactory: ConnectionFactory) = DatabaseClient.create(connectionFactory)

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val populator = ResourceDatabasePopulator(ClassPathResource("ddl.sql"))
        initializer.setDatabasePopulator(populator)

        return initializer
    }
}