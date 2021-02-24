package com.ldh.coin.notification.common;

/**
 * @author mango
 */

public enum CoinEnum {

    // 常用浏览器类型
    /*
     * Google chrome
     */
    CHROME("Google-Chrome", "Chrome"),
    /*
     * Mozilla firefox
     */
    FIREFOX("Mozilla-Firefox", "Firefox"),
    /*
     * Microsoft edge
     */
    Edge("Microsoft-Edge", "Edge"),
    /*
     * IE
     */
    IE("Internet-Explorer", "IE"),
    /*
     * Opera
     */
    OPERA("Opera", "Opera"),
    /*
     * Safari
     */
    SAFARI("Apple-Safari", "Safari"),

    ;


    private final String name;
    private final String code;

    CoinEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (CoinEnum c : CoinEnum.values()) {
            if (code.equals(c.code)) {
                return c.name;
            }
        }
        return null;
    }
}
