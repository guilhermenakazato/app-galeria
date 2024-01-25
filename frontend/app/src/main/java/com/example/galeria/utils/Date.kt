package com.example.galeria.utils

import java.time.Instant
import java.time.ZoneId

data class Date(val day: String, val month: String, val year: String) {
    companion object {
        private val translatedMonths = mapOf(
            "JANUARY" to "Janeiro",
            "FEBRUARY" to "Fevereiro",
            "MARCH" to "Março",
            "APRIL" to "Abril",
            "MAY" to "Maio",
            "JUNE" to "Junho",
            "JULY" to "Julho",
            "AUGUST" to "Agosto",
            "SEPTEMBER" to "Setembro",
            "OCTOBER" to "Outubro",
            "NOVEMBER" to "Novembro",
            "DECEMBER" to "Dezembro",
        )

        fun convertEpoch(epochSeconds: Long) : Date {
            val instant = Instant.ofEpochSecond(epochSeconds).atZone(ZoneId.of("America/Campo_Grande"))
            return Date(
                instant.dayOfMonth.toString(),
                translatedMonths[instant.month.toString()].toString(),
                instant.year.toString()
            )
        }
    }

    override fun toString(): String {
        return "Dia: ${this.day}, mês: ${this.month}, ano: ${this.year}"
    }
}