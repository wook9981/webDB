package com.webdb.api.util;

public class ServiceException  extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(String code, String message) {
        super(message);
        
        this.code = code;
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
