package cn.xiaoniu.cloud.server.util.ip;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP 工具类
 *
 * @author 孔明
 * @date 2019/11/18 10:49
 * @description cn.xiaoniu.cloud.server.util.ip.IPUtil
 */
public class IPUtil {

    private IPUtil() {
    }

    private static final String[] HEADERS = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP"};

    /**
     * 获取HttpServletRequest中的IP地址
     *
     * @param request 获取HttpServletRequest中的IP地址
     * @return IP
     * @see HttpServletRequest
     */
    public static String getIpFromRequest(HttpServletRequest request) {
        for (String header : HEADERS) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }
        }

        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr.contains(CommonConstant.CHAR_COMMA)) {
            return remoteAddr.substring(0, remoteAddr.indexOf(CommonConstant.CHAR_COMMA));
        }
        return remoteAddr;
    }

    /**
     * 获取本机IP地址, 返回IP地址, 可能为null
     *
     * @return java.lang.String
     * @author 孔明
     * @date 2020-01-13 16:13:46
     */
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /**
     * 获取域名对应的IP,返回解析得到的IP,可能为null
     *
     * @param domain
     * @return java.lang.String
     * @author 孔明
     * @date 2020-01-13 16:16:13
     */
    public static String getIPByDomain(String domain) {
        try {
            return InetAddress.getByName(domain).getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /**
     * 根据域名获得主机所有的IP地址
     *
     * @param hostName
     * @return void
     * @author 孔明
     * @date 2020-01-13 16:17:29
     */
    public static String[] getAllIPByName(String hostName) {
        try {
            InetAddress[] addrs = InetAddress.getAllByName(hostName);
            String[] ips = new String[addrs.length];
            for (int i = 0; i < addrs.length; i++) {
                ips[i] = addrs[i].getHostAddress();
            }
            return ips;
        } catch (UnknownHostException e) {
            return new String[0];
        }
    }

}
