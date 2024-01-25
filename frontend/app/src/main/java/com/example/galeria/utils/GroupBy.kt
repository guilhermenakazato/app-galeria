package com.example.galeria.utils

import com.example.frontend.model.Media

class GroupBy {
    companion object {
        fun <T: Media> year(mediaUnformattedMap: MutableMap<Long, T>): Map<String, MutableList<T>> {
            val mediaMap = mutableMapOf<String, MutableList<T>>()

            mediaUnformattedMap.forEach {
                val key = it.value.date.year

                if(key in mediaMap) {
                    mediaMap[key]?.add(it.value)
                } else {
                    mediaMap[key] = mutableListOf(it.value)
                }
            }

            return mediaMap
        }

        fun <T: Media> month(mediaUnformattedMap: MutableMap<Long, T>): Map<Pair<String, String>, MutableList<T>> {
            val mediaMap = mutableMapOf<Pair<String, String>, MutableList<T>>()

            mediaUnformattedMap.forEach {
                val month = it.value.date.month
                val year = it.value.date.year
                val key = Pair(month, year)

                if(key in mediaMap) {
                    mediaMap[key]?.add(it.value)
                } else {
                    mediaMap[key] = mutableListOf(it.value)
                }
            }

            return mediaMap
        }

        fun <T: Media> day(mediaUnformattedMap: MutableMap<Long, T>): Map<Triple<String, String, String>, MutableList<T>> {
            val mediaMap = mutableMapOf<Triple<String, String, String>, MutableList<T>>()

            mediaUnformattedMap.forEach {
                val day = it.value.date.day
                val month = it.value.date.month
                val year = it.value.date.year
                val key = Triple(day, month, year)

                if(key in mediaMap) {
                    mediaMap[key]?.add(it.value)
                } else {
                    mediaMap[key] = mutableListOf(it.value)
                }
            }

            return mediaMap
        }
    }
}