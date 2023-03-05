package com.pawporation.petpilot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform