package com.example.cabineperola.services.exceptions;

public class AccesDeniedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AccesDeniedException(String msg) {
		super(msg);
	}
	
	public AccesDeniedException(String msg, Throwable cause) {
		super(msg, cause);
	}
	

}
