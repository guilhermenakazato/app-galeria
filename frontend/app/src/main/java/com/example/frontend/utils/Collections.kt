package com.example.frontend.utils

fun IntArray.containsOnly(num: Int): Boolean = filter { it == num }.isNotEmpty()