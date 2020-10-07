# btc-test
Test Project for Back-end Engineer (Kotlin)

- A simple Reactive API to register a BTC transactions and query them by a datetime range 

### Tech Stack
- Kotlin
- Spring Boot
- Reactor
- Postgresql

### Database

Run the sql script to create database components. There are two scripts, both wil work but you can select based on throughput of writes and reads. 

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