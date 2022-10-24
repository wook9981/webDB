package com.webdb.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.client.result.UpdateResult;
import com.webdb.api.domain.LicenseList;
import com.webdb.api.domain.PolicyUtil;
import com.webdb.api.dto.LicenseDTO;
import com.webdb.api.util.AutoTypeUtil;
import com.webdb.api.util.StringUtil;

@Controller
@RequestMapping("/license")
public class LicenseListController {
	private static final Logger logger = LoggerFactory.getLogger(LicenseListController.class);

	@Autowired
    MongoTemplate mongoTemplate;
	
	/**
	 * 면허 등록
	 */
	@ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public Map<String, Object> saveLicense(HttpServletRequest request, LicenseDTO license) {
        try {
        	Query query = new Query(Criteria.where("LicenseCode").is(license.getLicenseCode()));
        	Boolean isExist = mongoTemplate.exists(query, LicenseList.class, "LicenseList");
        	if(isExist){ return PolicyUtil.newFailureMap("-1","코드 중복"); }//예외처리
        	
            Boolean isUsing = Boolean.parseBoolean(request.getParameter("using"));
            LicenseList input = new LicenseList();
            input.LicenseCode = license.getLicenseCode();
            input.LicenseName = license.getLicenseName();
            input.ViewName = license.getViewName();
            input.KeyMap = AutoTypeUtil.toQWERT(license.getLicenseName());
            input.IsUsing = isUsing ? "1":"0";
            
            mongoTemplate.insert(input, "LicenseList");
            
            Map<String, Object> resultMap = PolicyUtil.newSuccessMap();
        	return resultMap;
        } catch (Exception e) {
        	
        	logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap("-100", e.getMessage());
        }
    }
	
	/**
     * 면허 수정
     */
	@ResponseBody
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public Map<String, Object> updateLicense(
            HttpServletRequest request, LicenseDTO license) {
        try {
        	Boolean isUsing = Boolean.parseBoolean(request.getParameter("using"));
        	Query query = new Query(Criteria.where("LicenseCode").is(license.getLicenseCode()));
        	Update upquery = new Update();
        	upquery.set("LicenseName", license.getLicenseName());
        	upquery.set("ViewName", license.getViewName());
        	upquery.set("KeyMap", AutoTypeUtil.toQWERT(license.getLicenseName()));
        	upquery.set("IsUsing", isUsing ? "1":"0");
        	
        	UpdateResult result = mongoTemplate.updateFirst(query, upquery, LicenseList.class, "LicenseList");
        	
        	Map<String, Object> resultMap = PolicyUtil.newSuccessMap();
			resultMap.put("resultId", result.toString());
			return resultMap;
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
        }
    }
	
	/**
     * 면허 삭제
     */
	@ResponseBody
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public Map<String, Object> deleteLicense(@RequestParam("licenseCode") List<String> codes) {
        try {
        	Query query = new Query(Criteria.where("LicenseCode").in(codes));
        	List<LicenseList> result = mongoTemplate.findAllAndRemove(query, "LicenseList");
        	
        	Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
			jsonMap.put("licenselist", result);
			return jsonMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
        }
     }
	
	/**
     * 자동완성
     */
	@ResponseBody
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public Map<String, Object> searchLicense(LicenseDTO license)
    {
        String keyword = license.getQuery();
        try {

			Query dslQuery = new Query(Criteria.where("IsUsing").is("1"));
			
	    	if(NumberUtils.isDigits(keyword) && keyword.length() >= 4)
	    	{
		    	dslQuery.addCriteria(Criteria.where("LicenseCode").is(keyword));
	    	}
	    	else
	        {
		    	Pattern keywordPattern = Pattern.compile(AutoTypeUtil.toQWERT(keyword));
		    	dslQuery.addCriteria(Criteria.where("KeyMap").regex(keywordPattern));
	    	}
	    	
		    long totalcount = mongoTemplate.count(dslQuery, LicenseList.class);
		    int page = license.getPage();
		    int limit = license.getLimit();
		    dslQuery.skip((page -1) * limit).limit(limit);
        	List<LicenseList> mLicenseList = mongoTemplate.find(dslQuery, LicenseList.class, "LicenseList");
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for(LicenseList a: mLicenseList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap.put("code", a.LicenseCode);
        		tempMap.put("name", a.LicenseName);
        		result.add(tempMap);
        	}
			
			Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
			jsonMap.put("licenselist", result);
			jsonMap.put("totalcount", totalcount);

			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
		}
    }
	
	/**
     * 면허 목록
     */
	@ResponseBody
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public Map<String, Object> licenseList(LicenseDTO license) {
        try
        {
        	Query query = new Query();
		    //parentCode
		    String parentCode = license.getParent();
		    if (StringUtils.isNotEmpty(parentCode)) {
		    	// 시작 두글자가 같은 것 찾기
		    	Pattern keywordPattern = Pattern.compile("^" +parentCode.substring(0, 2));
	        	query.addCriteria(Criteria.where("LicenseCode").regex(keywordPattern));
		    }
		    
		    //keyword
		    String keyword = license.getQuery();
		    String queryType = license.getQueryType();
		    if (StringUtils.isNotEmpty(keyword)) {
		    	Pattern keywordPattern = Pattern.compile(keyword);
		    	if("1".equals(queryType)) {
		    		query.addCriteria(Criteria.where("LicenseCode").regex(keywordPattern));
		    	}else if("2".equals(queryType)) {
		    		query.addCriteria(Criteria.where("LicenseName").regex(keywordPattern));
		    	}else if("3".equals(queryType)) {
		    		query.addCriteria(Criteria.where("ViewName").regex(keywordPattern));
		    	}
		    }
		    
		    long totalCnt = mongoTemplate.count(query, LicenseList.class);
		    if (license.isPageable()) {	    	
		    	query.skip((license.getPage() -1) * license.getLimit()).limit(license.getLimit());
		    }
		    
		    List<LicenseList> mLicenseList = mongoTemplate.find(query, LicenseList.class, "LicenseList");
        	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        	for(LicenseList a: mLicenseList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap.put("view", a.ViewName);
        		tempMap.put("code", a.LicenseCode);
        		tempMap.put("name", a.LicenseName);
        		tempMap.put("using", a.IsUsing.equals("1") ? true:false);
        		jsonList.add(tempMap);
        	}
        	
        	Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
 			jsonMap.put("licenselist", jsonList);
 			jsonMap.put("totalcount", totalCnt);

			return jsonMap;
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
		}
    }
	
	/**
     * 면허 상세정보
     */
    @ResponseBody
    @RequestMapping(value="/info", method=RequestMethod.GET)
    public Map<String, Object> searchLicense(@RequestParam(value = "query", required = true) String query) {
		try {
			Query dslQuery = new Query();
			//Pattern keywordPattern = Pattern.compile(query);
			dslQuery.addCriteria(Criteria.where("LicenseName").is(query));
			
			LicenseList license = mongoTemplate.findOne(dslQuery, LicenseList.class, "LicenseList");
			
			Map<String, Object> mlicense = new HashMap<String, Object>();
			mlicense.put("name", StringUtil.defaultEmpty(license.LicenseName));
			mlicense.put("code", StringUtil.defaultEmpty(license.LicenseCode));
			
		    Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
		    jsonMap.put("license", mlicense);
			
			
			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
		}
	}
    
    /**
     * 면허명, 단축명 목록
     */
    @ResponseBody
    @RequestMapping(value = "/namelist", method = RequestMethod.GET)
    public List<Map<String, Object>> licenseNameList() {
        try {
        	List<LicenseList> mLicenseList = mongoTemplate.findAll(LicenseList.class);

        	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        	for(LicenseList a: mLicenseList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap.put("view", a.ViewName);
        		tempMap.put("name", a.LicenseName);
        		jsonList.add(tempMap);
        	}
            return jsonList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
            return jsonList;
        }
    }

}
