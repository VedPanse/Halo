package org.halo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform