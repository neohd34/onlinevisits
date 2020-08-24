package me.chat.onlinevisits.geoip

import com.fasterxml.jackson.annotation.JsonProperty

data class Country(@JsonProperty("country_code") val cc: String)