package me.chat.onlinevisits.geoip;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "geoip", url = "https://geoip-db.com/json")
public interface GeoIpHttpClient {

    @RequestMapping("/{ip}")
    Country getCountry(@PathVariable("ip") String ip);

}
