package com.webdb.api.dto;

import java.util.HashMap;
import java.util.Map;

public class WebdbDTO {
    public static Map<String, String> areaViewNameMap;
    
    static {
        areaViewNameMap = new HashMap<String, String>();
    }
    
    public static String getAreaViewName(String areaName) {
    	String viewName = areaViewNameMap.get(areaName);
    	
    	if (viewName == null) {
    		return areaName;
    	}
    	
        return viewName;
    }
}
