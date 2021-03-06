# btc-test
Test Project for Back-end Engineer (Kotlin)

- A simple Reactive API to register a BTC transactions and query them by a datetime range 

### Tech Stack
- Kotlin
- Spring Boot
- Reactor
- Postgresql

### Database

Run the sql script to create database components. There are two scripts, both will work but you can select based on throughput of writes and reads. 

- Run ddl.sql if writes are the priority, higher throughput on the register transaction endpoint.
- Run ddl-materialized-view.sql if reads have the priority, it means a higher throughput on fetching transactions endpoint.

I also included a Dockerfile to create Postgresql database container if needed.

### API

`/api/v1/btc/transactions`

`POST` Register a transaction

Header

```
Content-Type application/json
```

Request body
```json
{
  "datetime": "2020-10-05T10:15:34-03:00",
  "amount": 3.5
}
```

Response body
```json
{
  "id": 12,
  "datetime": "2020-10-05T13:15:34Z",
  "amount": 3.5
}
```

`GET` Get history by datetime range

Request Params:
- startDatetime
- endDatetime

Encoded URL example
`/api/v1/btc/transactions?startDatetime=2020-10-04T15%3A45%3A00%2B07%3A00&endDatetime=2020-10-06T15%3A45%3A11%2B07%3A00` 

Response body
```json
[
  {
    "datetime": "2020-10-05T08:15:34",
    "amount": 3.5
  },
  {
    "datetime": "2020-10-05T08:15:35",
    "amount": 13.7
  },
  {
    "datetime": "2020-10-05T08:15:35",
    "amount": 21.2
  }
]
```

**Note**: To run the app this command can be used
`DB_PORT=<port> DB_HOST=<host> DB_NAME=<database_name> DB_USERNAME=<username> DB_PASSWORD=<password> ./gradlew botRun`
Please provide valid values for the database connection.

### Example:
After register these transactions:
```json
{
  "datetime": "2020-10-13T10:00:00+07:00",
  "amount": 1.0
}

{
  "datetime": "2020-10-13T11:00:00+07:00",
  "amount": 1.0
}

{
  "datetime": "2020-10-13T12:00:00+07:00",
  "amount": 1.0
}

{
  "datetime": "2020-10-13T12:10:00+07:00",
  "amount": 1.0
}

{
  "datetime": "2020-10-13T12:20:00+07:00",
  "amount": 1.0
}

```

Then a request to fetch history between datetimes: 2020-10-13T11:00:00+07:00 and 2020-10-13T12:30:00+07:00
http://localhost:8080/api/v1/btc/transactions?startDatetime=2020-10-13T11%3A00%3A00%2B07%3A00&endDatetime=2020-10-13T12%3A30%3A00%2B07%3A00

Response:
```json
[
  {
    "datetime":"2020-10-13T00:00:00",
    "amount":5.0,
  },
  {
    "datetime":"2020-10-12T23:00:00",
    "amount":2.0,
  }
]
```
