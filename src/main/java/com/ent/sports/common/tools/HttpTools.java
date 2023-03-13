package com.ent.sports.common.tools;

import lombok.extern.slf4j.Slf4j;
import net.ipip.ipdb.City;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class HttpTools {

    private static City city_DB;

    static {
        try {
            city_DB = new City(new HttpTools().getClass().getResource("/").getPath() + "ipipfree.ipdb");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取请求头中的token
     *
     * @return
     */
    public static String getHeaderToken() {
        return getRequest().getHeader("token_id");
    }

    /**
     * 解析http请求ip
     *
     * @return
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddresses) || "0:0:0:0:0:0:0:1".equals(ipAddresses)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取local host信息异常:{}", e.getMessage());
                }
                ipAddresses = inet.getHostAddress();
            }
        }
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        return ip;
    }

    /**
     * 通过IP查询城市
     *
     * @param ip (IPv4或者 IPv6)
     * @return 例如[中国, 广东, 深圳]
     */
    public static String[] findCityByIp(String ip) {
        try {
            return city_DB.find(ip, "CN");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据ip获取解析后的地址全称
     *
     * @param ip
     * @return
     */
    public static String getCityDataByIp(String ip) {
        String[] cityArray = findCityByIp(ip);
        if (cityArray != null && cityArray.length > 0) {
            return String.join("-", cityArray);
        }
        log.error("ip:{}获取城市信息失败",ip);
        return "";
    }

    /**
     * 获取城市地址
     * @return
     */
    public static String getCityData() {
        String ip = HttpTools.getIp();
        String[] cityArray = findCityByIp(ip);
        if (cityArray != null && cityArray.length > 0) {
            return String.join("-", cityArray);
        }
        log.error("ip:{}获取城市信息失败",ip);
        return "";
    }

}
