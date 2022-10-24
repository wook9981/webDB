package com.webdb.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	/**
	 * null ? null : toString
	 *
	 * @param obj
	 * @return String
	 */
	public static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
     * null ? "" : toString
     *
     * @param obj
     * @return String
     */
    public static String defaultEmpty(Object obj) {
        return defaultString(obj, "");
    }

	/**
	 * null ? default : toString
	 *
	 * @param obj
	 * @param def
	 * @return String
	 */
	public static String defaultString(Object obj, String def) {
		return obj == null ? def : toString(obj);
	}

	/**
     * obj1 != obj2 ?
     *
     * @param obj1
     * @param obj2
     * @return boolean
     */
    public static boolean isNotEquals(Object obj1, Object obj2) {
        return !isEquals(obj1, obj2);
    }

	/**
	 * obj1 == obj2 ?
	 *
	 * @param obj1
	 * @param obj2
	 * @return boolean
	 */
	public static boolean isEquals(Object obj1, Object obj2) {
	    if (obj1 == null || obj2 == null) {
	        return false;
	    }

	    return toString(obj1).equals(toString(obj2));
	}

	/**
     * is not empty and is not "0"
     *
     * @param str
     * @return boolean
     */
    public static boolean isNotAll(String str) {
        return !isAll(str);
    }

	/**
	 * is empty or is "0"
	 *
	 * @param str
	 * @return boolean
	 */
	public static boolean isAll(String str) {
	    return StringUtils.isEmpty(str) || str.equals("0");
	}

	public static boolean containsAny(String str, String... searchs) {
	    for (String search : searchs) {
	        if (StringUtils.containsIgnoreCase(str, search)) {
	            return true;
	        }
	    }

	    return false;
	}

	public static String containsAnyReturn(String str, String... searchs) {
	    for (String search : searchs) {
	        if (StringUtils.containsIgnoreCase(str, search)) {
	            return search;
	        }
	    }

	    return null;
	}

	public static boolean equalsAny(String str, String... searchs) {
	    for (String search : searchs) {
	        if (StringUtils.equalsIgnoreCase(str, search)) {
	            return true;
	        }
	    }

	    return false;
	}

	/**
	 * "가나다" -> {"가", "나", "다"}
	 *
	 * @param str
	 * @return Character[]
	 */
	public static List<String> toStringArray(String str) {
		String[] tmp = str.split("");
		List<String> list = new ArrayList<String>();

		for (String s : tmp) {
			if (StringUtils.isNotEmpty(s)) {
				list.add(s);
			}
		}

	    return list;
	}

	/**
	 * trim, replace ASCII 160
	 *
	 * @param str
	 * @return String
	 */
	public static String trim(String str) {
	    if (str != null) {
	        str = str.trim().replaceAll(" ", "");
	    }

	    return str;
	}

	public static String encodeURI(String str, String charset) throws UnsupportedEncodingException {
	    return URLEncoder.encode(str, charset).replaceAll("\\+", "%20");
	}

	/**
	 * null ? true : false
	 *
	 * @param obj
	 * @return Boolean
	 */
	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}

	/**
	 * String -> HashMap
	 *
	 * @param value
	 * @return
	 */
	public static Map<String, Object>  stringToHashMap(String value){
		value = value.substring(1, value.length()-1);

		String[] keyValuePairs = value.split(",");
		Map<String, Object> map = new HashMap<String, Object>();

		for(String pair : keyValuePairs){
		    String[] entry = pair.split("=");
		    map.put(entry[0].trim(), entry[1].trim());
		}

		return map;
	}

	/**
	 * String -> List
	 *
	 * @param value
	 * @param delimiter
 	 * @return List<String>
	 */
	public static List<String>  stringToList(String value, String delimiter){
		return Arrays.asList(value.split(delimiter));
	}

	/**
	 *
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 *
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		String ext = "";
		int i = fileName.lastIndexOf(".");
		
		if (i != -1) {
			ext = fileName.substring(i);
		}
		
		return ext;
	}
}
