package com.proptit.protify.models

data class LoginResponse (
    val accessToken: String,
    val refreshToken: String
)