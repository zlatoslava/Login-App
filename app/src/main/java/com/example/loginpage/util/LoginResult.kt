package com.example.loginpage.util

enum class LoginResult(val value: Int) {
    EMPTY_PASSWORD(0),
    EMPTY_USERNAME(1),
    SHORT_PASSWORD(2),
    LONG_USERNAME(3),
    LOGIN_ERROR(4),
    SUCCESSFUL(6),
    OK(7),
    PASSWORD_CONFIRMATION_ERROR(8),
}