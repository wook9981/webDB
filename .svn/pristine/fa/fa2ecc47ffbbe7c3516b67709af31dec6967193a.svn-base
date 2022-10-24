package com.webdb.api.domain;

import java.util.HashMap;
import java.util.Map;

public class PolicyUtil {

    public static Map<String, Object> newSuccessMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", true);
        map.put("statuscd", "1");
        map.put("statusmsg", "Success.");

        return map;
    }

    public static Map<String, Object> newFailureMap() {
        return newFailureMap("0", "Server error.");
    }

    /**
     * @param code (default:0)
     * @param msg
     * @return
     */
    public static Map<String, Object> newFailureMap(String code, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", false);
        map.put("statuscd", code);
        map.put("statusmsg", msg);

        return map;
    }
}
