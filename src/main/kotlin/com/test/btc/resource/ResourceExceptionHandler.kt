package com.test.btc.resource

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(value = [WebExchangeBindException::class])
    fun handleMethodArgumentNotValid(exception: WebExchangeBindException): ResponseEntity<ObjectNode> {
        val body = JsonNodeFactory.instance.objectNode()
        body.putPOJO(
                "errors",
                exception
                        .bindingResult
                        .allErrors
                        .mapNotNull { it.defaultMessage })
                .toList()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ResponseStatusException::class])
    fun handleGeneralException(exception: ResponseStatusException): ResponseEntity<ObjectNode> {
        val body = JsonNodeFactory.instance.objectNode()
        body.put("error", exception.message)
        return ResponseEntity(body, exception.status)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ObjectNode> {
        val body = JsonNodeFactory.instance.objectNode()
        body.put("error", exception.message)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}