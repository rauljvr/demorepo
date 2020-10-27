package com.demo.exceptionshandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ForbiddenTransferException extends Exception {

	private static final long serialVersionUID = 1L;

	public ForbiddenTransferException(String message) {
		super(message);
	}
}
