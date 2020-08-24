package me.chat.onlinevisits.geoip

import me.chat.common.General.UNKNOWN
import org.springframework.stereotype.Service

@Service
class CountryParser(private val httpClient: GeoIpHttpClient) {

    /**
     * Try to fetch country by IP address
     */
    fun parse(ip: String?): String {

        if (ip == null) return UNKNOWN

        val cc = try {
            httpClient.getCountry(ip)?.cc
        } catch (e: Exception) {
            UNKNOWN
        }

        return if (cc == null || cc.length != 2) UNKNOWN else cc
    }

}