package com.shiryaev.domain.utils

fun <T: Any?> nonNullValues(values: List<T?>): List<T> = values.filterNotNull()