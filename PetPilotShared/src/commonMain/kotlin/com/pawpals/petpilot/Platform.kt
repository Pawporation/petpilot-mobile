package com.pawpals.petpilot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform