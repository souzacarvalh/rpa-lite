package org.ffsc.rpa.exceptions;

public interface ExceptionHandler<T extends Throwable, R extends Exception> {

	public T handle(R exception);
}