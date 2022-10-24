package com.webdb.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ItemList")
public class ItemList {
	
	@Id   //요것이 고유 키 값
	private String _id;

	@Field(name = "ItemCode")
	public String ItemCode;
	
	@Field(name = "ItemName")
	public String ItemName;
	
	@Field(name = "ParentCode")
	public String ParentCode;
	
	@Field(name = "Depth")
	public String Depth;
	
	@Field(name = "KeyMap")
	public String KeyMap;
	
	@Field(name = "ViewName")
	public String ViewName;
}
