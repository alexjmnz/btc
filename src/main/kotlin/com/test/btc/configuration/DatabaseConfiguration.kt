package com.test.btc.configuration

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.DatabaseClient

@Configuration
class DatabaseConfiguration(val properties: Database) {

    @Bean
    fun connectionFactory() = PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
            .host(properties.host)
            .port(properties.port)
            .database(properties.name)
            .username(properties.username)
            .password(properties.password)
            .schema(properties.schema)
            .build())

    @Bean
    fun databaseClient(connectionFactory: PostgresqlConnectionFactory) = DatabaseClient.create(connectionFactory)
}