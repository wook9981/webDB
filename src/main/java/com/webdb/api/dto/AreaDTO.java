package com.webdb.api.dto;

public class AreaDTO extends PagingDTO<AreaDTO> {
	private String areaCode;
	private String siDo;
	private String siGun;
	private String areaName;
	private String viewName;
	private String depth;
	private String keyMap;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSiDo() {
		return siDo;
	}

	public void setSiDo(String siDo) {
		this.siDo = siDo;
	}

	public String getSiGun() {
		return siGun;
	}

	public void setSiGun(String siGun) {
		this.siGun = siGun;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

    public String getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(String keyMap) {
        this.keyMap = keyMap;
    }
}
