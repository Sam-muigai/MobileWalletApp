package com.app.compulynx.data.mappers

import com.app.compulynx.core.network.dtos.LoginRequestDto
import com.app.compulynx.domain.models.LoginRequest

fun LoginRequest.toDomain(): LoginRequestDto {
    return LoginRequestDto(
        customerId = customerId,
        pin = pin
    )
}