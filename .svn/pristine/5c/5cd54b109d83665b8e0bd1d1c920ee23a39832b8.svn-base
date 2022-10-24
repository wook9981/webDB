package com.webdb.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "LicenseList")
public class LicenseList {

	@Id   //요것이 고유 키 값
	private String _id;

	@Field(name = "LicenseCode")
	public String LicenseCode;
	
	@Field(name = "LicenseName")
	public String LicenseName;
	
	@Field(name = "ViewName")
	public String ViewName;
	
	@Field(name = "KeyMap")
	public String KeyMap;
	
	@Field(name = "IsUsing")
	public String IsUsing;
}
