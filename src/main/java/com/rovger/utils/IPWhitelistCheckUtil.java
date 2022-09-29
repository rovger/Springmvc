package com.rovger.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IPWhitelistCheckUtil {

    public static final String DOT_SIGN = ".";
    public static final String ESCAPE_SIGN = "\\";
    public static final String DASH_SIGN = "-";
    public static final String SEMI_COLON_SIGN = ";";

    final static class CidrIPRange {
        private String minIP;   //cidr表示的ip段的最小ip值
        private String maxIP;   //cidr表示的ip段的最大ip值

        public String getMinIP() {
            return minIP;
        }

        public void setMinIP(String minIP) {
            this.minIP = minIP;
        }

        public String getMaxIP() {
            return maxIP;
        }

        public void setMaxIP(String maxIP) {
            this.maxIP = maxIP;
        }

        @Override
        public String toString() {
            return "CidrIPResult [minIP=" + minIP + ", maxIP=" + maxIP + "]";
        }

    }

    private static final Logger LOG = LoggerFactory.getLogger(IPWhitelistCheckUtil.class);
    private static final String NO_NEED_CHECK_IP_CONFIG = "0.0.0.0/0";   //0.0.0.0/0表示不需要ip白名单校验

    /**
     * ip白名单检查
     *
     * @param realIP
     * @param whiteList: 以';'隔分开的ip地址白名单列表
     * @return
     */
    public static boolean ifRealIPExistInWhiteList(String realIP, String whiteList) {
        if (StringUtils.isNotBlank(whiteList) && whiteList.contains(NO_NEED_CHECK_IP_CONFIG)) {
            return true;
        }
        boolean existed = false;
        if (StringUtils.isNotBlank(realIP) && StringUtils.isNotBlank(whiteList)) {
            String[] whiteListArr = whiteList.split(SEMI_COLON_SIGN);
            for (String whiteIP : whiteListArr) {
                existed = realIP.equals(whiteIP) || ifRealIPExistInIPRange(whiteIP, realIP);
                if (existed) {
                    break;
                }
            }
        }
        return existed;
    }

    public static void main(String[] args) {
        System.out.println(ifRealIPExistInIPRange("192.168.2.0/24", "192.168.2.22"));
    }

    private static boolean ifRealIPExistInIPRange(String ipRange, String realIP) {
        // 包含‘/’，例如172.16.0.0/12，则为CIDR表示的ip范围
        if (ipRange.contains("/")) {
            return ifRealIPExistInCidrIP(ipRange, realIP);
        }
        if (!ipRange.contains(DASH_SIGN)) {
            return false;
        }
        /**
         * 包含‘-’，例如172.16.0.0-255，则表示自定义的简明ip范围表示法
         */
        boolean realIPExistInIPRange = false;
        String fixedIPPart = getFixedIPPart(ipRange);
        String rangePart = getRangePart(ipRange);
        String[] rangePartSegs = rangePart.split(ESCAPE_SIGN + DASH_SIGN);
        int lowerBound = Integer.parseInt(rangePartSegs[0]);
        int upperBound = Integer.parseInt(rangePartSegs[1]);
        for (int j = lowerBound; j <= upperBound; j++) {
            if (realIP.equals(fixedIPPart + j)) {
                realIPExistInIPRange = true;
                break;
            }
        }
        return realIPExistInIPRange;
    }

    private static String getFixedIPPart(String ipRange) {
        String[] ips = ipRange.split(ESCAPE_SIGN + DOT_SIGN);
        StringBuilder fixedIPPartBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            fixedIPPartBuilder = fixedIPPartBuilder.append(ips[i]).append(DOT_SIGN);
        }
        return fixedIPPartBuilder.toString();
    }

    private static String getRangePart(String ipRange) {
        StringBuilder rangePartBuidler = new StringBuilder();
        rangePartBuidler = rangePartBuidler.append(ipRange.split(ESCAPE_SIGN + DOT_SIGN)[3]);
        return rangePartBuidler.toString();
    }

    private static boolean ifRealIPExistInCidrIP(String cidrIP, String realIP) {
        if (cidrIP == null || "".equals(cidrIP.trim())) {
            throw new RuntimeException("the format of your app's ip_whitelist [" + cidrIP + "] is wrong, it contains blank string");
        }
        String[] ipIds = cidrIP.split("\\/");
        if (ipIds == null || ipIds.length != 2) {
            throw new RuntimeException("the format of your app's ip_whitelist [" + cidrIP + "] is wrong, right format of CIDR is x.x.x.x/n");
        }
        int num = Integer.parseInt(ipIds[1]);
        if (num > 32 || num < 4) {
            throw new RuntimeException("the format of your app's ip_whitelist [" + cidrIP + "] is wrong, network ID of CIDR must be in(4,32)");
        }
        CidrIPRange cidrIPResult = getCidrIPRange(cidrIP);
        long realIPValue = ipToNumeric(realIP);
        long minCidrIPValue = ipToNumeric(cidrIPResult.getMinIP());
        long maxCidrIPValue = ipToNumeric(cidrIPResult.getMaxIP());
//		LOG.warn(String.format("realIP: %s, cidrIP: %s, cidrIPResult: %s", realIP, cidrIP, cidrIPResult.toString())); 
        return (realIPValue >= minCidrIPValue) && (realIPValue <= maxCidrIPValue);
    }

    /*
     * 获取网络IP，即也是CIDR表示的最小IP和最大IP
     *
     * @param ipCidr CIDR法表示的IP，例如：200.6.12.55/21
     *
     * @return 网络IP最小最大值
     */
    private static CidrIPRange getCidrIPRange(String ipCidr) {
        String[] ipMaskLen = ipCidr.split("\\/");
        String mask = getMask(Integer.parseInt(ipMaskLen[1])); // 得到掩码：200.6.12.55/21的掩码是 255.255.248.0
        String[] ips = ipMaskLen[0].split("\\.");
        String[] maskArr = mask.split("\\.");
        StringBuffer sbMinIP = new StringBuffer();
        StringBuffer sbMaxIP = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int minNetIPValue = Integer.parseInt(ips[i]) & Integer.parseInt(maskArr[i]);
            sbMinIP.append(minNetIPValue);
            int maskRevertIPValue = (Integer.parseInt(maskArr[i]) ^ 0xFF) & 0xFF;
            sbMaxIP.append(minNetIPValue + maskRevertIPValue);
            if (i != 3) {
                sbMinIP.append(".");
                sbMaxIP.append(".");
            }
        }
        CidrIPRange cidrIPResult = new CidrIPRange();
        cidrIPResult.setMinIP(sbMinIP.toString());
        cidrIPResult.setMaxIP(sbMaxIP.toString());
        return cidrIPResult;
    }

    /*
     * 获取掩码
     *
     * @param maskLength 网络ID位数
     *
     * @return
     */
    private static String getMask(int maskLength) {
        int binaryMask = 0xFFFFFFFF << (32 - maskLength);
        StringBuffer sb = new StringBuffer();
        for (int shift = 24; shift > 0; shift -= 8) {
            sb.append(Integer.toString((binaryMask >>> shift) & 0xFF));
            sb.append(".");
        }
        sb.append(Integer.toString(binaryMask & 0xFF));
        return sb.toString();
    }

    /*
     * IP地址转换为一个256进制的long整数
     *
     * @param ip
     *
     * @return
     */
    private static long ipToNumeric(String ip) {
        String[] ips = ip.split("\\.");
        Long[] ipLong = new Long[4];
        for (int i = 0, len = ips.length; i < len; i++) {
            ipLong[i] = Long.parseLong(ips[i]);
        }
        long result = ipLong[3] & 0xFF;
        result |= ((ipLong[2] << 8)) & 0xFF00;
        result |= ((ipLong[1] << 16)) & 0xFF0000;
        result |= ((ipLong[0] << 24)) & 0xFF000000;
        return result;
    }

//	public static void main(String[] args) {
//		String realIP = "172.28.68.0";
//		String cidrIP = "0.0.0.0/0";
//		boolean result = ifRealIPExistInWhiteList(realIP, cidrIP);
//		System.out.println(result);
//	}

}  
