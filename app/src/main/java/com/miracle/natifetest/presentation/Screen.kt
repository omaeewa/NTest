package com.miracle.natifetest.presentation

const val ARGUMENT_GIF_INDEX = "argument_gif_link"

enum class Screen {
    Home, GifInfo
}

fun String.addArgs(name: String, value: String) = this.plus("?$name=$value&")

fun String.addPathArgs(key: String) = this.plus("?$key={$key}")