package com.webdb.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.webdb.api.domain.ItemList;
import com.webdb.api.domain.PolicyUtil;
import com.webdb.api.dto.ItemDTO;
import com.webdb.api.util.AutoTypeUtil;
import com.webdb.api.util.ServiceException;
import com.webdb.api.util.StringUtil;

@Controller
@RequestMapping("/item")
public class ItemListController {
	private static final Logger logger = LoggerFactory.getLogger(ItemListController.class);
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	/**
     * 물품 등록
     */
    @ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public Map<String, Object> saveItem(ItemDTO item) {
        try {
        	Query query = new Query(Criteria.where("ItemCode").is(item.getItemCode()));
        	Boolean isExist = mongoTemplate.exists(query, ItemList.class, "ItemList");
        	if(isExist){ return PolicyUtil.newFailureMap("-1","코드 중복"); }//예외처리
        	
        	String itemCode = item.getItemCode();
        	int length = item.getItemCode().length();
        	
        	switch (length)
            {
                 case 2:
                     item.setParentCode("0");
                     item.setDepth("1");
                     break;
                 case 4:
                     item.setParentCode(itemCode.substring(0,  2));
                     item.setDepth("2");
                     break;
                 case 6:
                     item.setParentCode(itemCode.substring(0,  4));
                     item.setDepth("3");
                     break;
                 case 8:
                     item.setParentCode(itemCode.substring(0,  6));
                     item.setDepth("4");
                     break;
                 case 10:
                     item.setParentCode(itemCode.substring(0,  8));
                     item.setDepth("5");
                     break;

                 default:
                     throw new ServiceException("-2", "ItemCode is short/long");
             }
        	
        	ItemList input = new ItemList();
            input.ItemCode = item.getItemCode();
            input.ItemName = item.getItemName();
            input.ParentCode = item.getParentCode();
            input.Depth = item.getDepth();
            input.KeyMap = AutoTypeUtil.toQWERT(item.getItemName());
            input.ViewName = item.getViewName();
            
            mongoTemplate.insert(input, "ItemList");
            
        	Map<String, Object> resultMap = PolicyUtil.newSuccessMap();
        	return resultMap;
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap("-100", e.getMessage());
        }
    }
    
    /**
     * 물품 수정
     */
    @ResponseBody
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public Map<String, Object> updateItem(ItemDTO item) {
    	try {
        	Query query = new Query(Criteria.where("ItemCode").is(item.getItemCode()));
        	Update upquery = new Update();
        	upquery.set("ItemName", item.getItemName());
        	upquery.set("KeyMap", AutoTypeUtil.toQWERT(item.getItemName()));
        	upquery.set("ViewName", item.getViewName());
        	
        	UpdateResult result = mongoTemplate.updateFirst(query, upquery, ItemList.class, "ItemList");
            
			Map<String, Object> resultMap = PolicyUtil.newSuccessMap();
			resultMap.put("resultId", result.getModifiedCount());
			return resultMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
        }
    }
	
	/**
     * 물품 삭제
     */
    @ResponseBody
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public Map<String, Object> deleteItem(@RequestParam("itemCode") List<String> codes) {
    	try {
        	Query query = new Query(Criteria.where("ItemCode").in(codes));
        	List<ItemList> result = mongoTemplate.findAllAndRemove(query, "ItemList");
        	

			Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
			jsonMap.put("itemlist", result);
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
    public Map<String, Object> searchItem(ItemDTO item)
    {
        String keyword = item.getQuery();
        try {

			Query dslQuery = new Query(Criteria.where("Depth").is("5"));

	    	if(NumberUtils.isDigits(keyword) && keyword.length() >= 10)
	    	{
		    	Pattern keywordPattern = Pattern.compile("^" + keyword);
		    	dslQuery.addCriteria(Criteria.where("ItemCode").regex(keywordPattern));
	    	}
	    	else
	        {
		    	Pattern keywordPattern = Pattern.compile(AutoTypeUtil.toQWERT(keyword));
		    	dslQuery.addCriteria(Criteria.where("KeyMap").regex(keywordPattern));
	    	}
	    	
		    long totalcount = mongoTemplate.count(dslQuery, ItemList.class);
		    int page = item.getPage();
		    int limit = item.getLimit();
		    dslQuery.skip((page -1) * limit).limit(limit);
        	List<ItemList> mItemList = mongoTemplate.find(dslQuery, ItemList.class, "ItemList");
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			for(ItemList a: mItemList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap.put("code", a.ItemCode);
        		tempMap.put("name", a.ItemName);
        		result.add(tempMap);
        	}
			
			Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
			jsonMap.put("itemlist", result);
			jsonMap.put("totalcount", totalcount);
			
			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return PolicyUtil.newFailureMap();
		}
    }
	
	/**
	 * 물품 목록
	 */
	@ResponseBody
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public Map<String, Object> itemList(ItemDTO item){
		try {
			Query query = new Query();
		    String parentCode = item.getParent();
		    
		    System.out.println(" 1. parentCode ==> "+ parentCode);
		    
		    if (StringUtils.isEmpty(parentCode)) {
		    	// 최초 로딩 시 Depth == 5 인걸로
	        	query.addCriteria(Criteria.where("Depth").is("5"));
		    } else {
		    	query.addCriteria(Criteria.where("ParentCode").is(parentCode));
		    	//query.addCriteria(Criteria.where("Depth").is("4"));
		    }
		    
		    //keyword
		    String keyword = item.getQuery();
		    String queryType = item.getQueryType();
		    if (StringUtils.isNotEmpty(keyword)) {
		    	Pattern keywordPattern = Pattern.compile(keyword);
		    	if("1".equals(queryType)) {
		    		query.addCriteria(Criteria.where("ItemCode").regex(keywordPattern));
		    	}else if("2".equals(queryType)) {
		    		query.addCriteria(Criteria.where("ItemName").regex(keywordPattern));
		    	}else if("3".equals(queryType)) {
		    		query.addCriteria(Criteria.where("ViewName").regex(keywordPattern));
		    	}
		    }

		    long totalCnt = mongoTemplate.count(query, ItemList.class);
		    if (item.isPageable()) {	    	
		    	query.skip((item.getPage() -1) * item.getLimit()).limit(item.getLimit());
		    }
			
		    System.out.println("1. Query ==> ["+query+"]");
		    
        	List<ItemList> mItemList = mongoTemplate.find(query, ItemList.class, "ItemList");
		    //List<ItemList> mItemList = mongoTemplate.findAll(ItemList.class); //전제 보여 주기_2022/10/14
		    
        	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        	for(ItemList a: mItemList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		
        		tempMap.put("view", a.ViewName);
        		tempMap.put("code", a.ItemCode);
        		tempMap.put("name", a.ItemName);
        		tempMap.put("dept", a.Depth);
        		tempMap.put("keymap", a.KeyMap);
        		
        		jsonList.add(tempMap);
        	}
        	
            Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
			jsonMap.put("itemlist", jsonList);
			jsonMap.put("totalcount", totalCnt);

			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
            return PolicyUtil.newFailureMap();
		}
	}
	
	/**
	 * 코드로 물품 상세정보
	 */
	@ResponseBody
	@RequestMapping(value="/info/code", method=RequestMethod.GET)
	public Map<String, Object> searchItemByItemCode(@RequestParam(value = "query", required = true) String query) {
		try {
			Query dslQuery = new Query();
			Pattern keywordPattern = Pattern.compile(query);
			dslQuery.addCriteria(Criteria.where("ItemCode").regex(keywordPattern));
			ItemList item = mongoTemplate.findOne(dslQuery, ItemList.class, "ItemList");
			
			Map<String, Object> mitem = new HashMap<String, Object>();
			mitem.put("name", StringUtil.defaultEmpty(item.ItemName));
			mitem.put("view", StringUtil.defaultEmpty(item.ViewName));
			
		    Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
		    jsonMap.put("item", mitem);
			
			
			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return PolicyUtil.newFailureMap();
		}
	}
	
	/**
	 * 물품 상세정보
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public Map<String, Object> searchItem(@RequestParam(value = "query", required = true) String query) {
		try {
			Query dslQuery = new Query();
			Pattern keywordPattern = Pattern.compile(query);
			dslQuery.addCriteria(Criteria.where("ItemName").regex(keywordPattern));
			
			ItemList item = mongoTemplate.findOne(dslQuery, ItemList.class, "ItemList");
			
			Map<String, Object> mitem = new HashMap<String, Object>();
			mitem.put("name", StringUtil.defaultEmpty(item.ItemName));
			mitem.put("code", StringUtil.defaultEmpty(item.ItemCode));
			
		    Map<String, Object> jsonMap = PolicyUtil.newSuccessMap();
		    jsonMap.put("item", mitem);

			return jsonMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return PolicyUtil.newFailureMap();
		}
	}
	
	/**
     * 물품명, 단축명 목록
     */
	@ResponseBody
    @RequestMapping(value = "/namelist", method = RequestMethod.GET)
    public List<Map<String, Object>> itemNameList() {
        try {
        	List<ItemList> mItemList = mongoTemplate.findAll(ItemList.class);

        	List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
        	for(ItemList a: mItemList) {
        		Map<String, Object> tempMap = new HashMap<String, Object>();
        		tempMap.put("view", a.ViewName);
        		tempMap.put("name", a.ItemName);
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
