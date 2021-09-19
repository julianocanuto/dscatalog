package com.julianocanuto.dscatalog.services.exceptions;

public class EntityNotFoundExcepetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundExcepetion(String msg) {
		super(msg);
	}

}
