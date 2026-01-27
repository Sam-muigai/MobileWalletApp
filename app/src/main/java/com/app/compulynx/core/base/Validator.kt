package com.app.compulynx.core.base

object Validator {
    fun validatePin(pin: String): String? {
        return when {
            pin.isEmpty() -> null
            pin.length < 4 -> "Pin must be contain exactly 4 digits"
            pin.length > 4 -> "Pin must be contain exactly 4 digits"
            else -> null
        }
    }

    fun validateCustomerId(customerId: String): String? {
        return when {
            customerId.isEmpty() -> null
            !customerId.startsWith("CUST") -> "Customer ID must start with CUST"
            else -> null
        }
    }

    fun validateAccountNo(accountNo: String): String? {
        return when {
            accountNo.isEmpty() -> null
            !accountNo.startsWith("ACT") -> "Account number must start with ACT"
            else -> null
        }
    }

    fun validateAmount(amount: String): String? {
        return when {
            amount.isEmpty() -> null
            amount.toDouble() <= 0 -> "Amount must be greater than zero"
            else -> null
        }
    }

}