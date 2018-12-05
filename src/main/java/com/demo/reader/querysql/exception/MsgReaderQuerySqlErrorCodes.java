package com.demo.reader.querysql.exception;

import com.demo.error.ErrorCode;

/**
 * 
 * @author sulwayj
 *
 */
public enum MsgReaderQuerySqlErrorCodes implements ErrorCode{
	
	VALIDATION_EXCEPTION(2001),
	CONVERSION_EXCEPTION(2002);
	
	private final int mCode;

	private MsgReaderQuerySqlErrorCodes(int number) {
		this.mCode = number;
	}
	
	/**
	 * Returns error code 
	 */
	public int getErrorCode() {
		return mCode;
	}
}