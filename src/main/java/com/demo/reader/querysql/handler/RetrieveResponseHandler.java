package com.demo.reader.querysql.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.error.CommonRuntimeException;
import com.demo.properties.PropertyHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.reader.querysql.exception.ConversionException;
import com.demo.reader.querysql.exception.MsgReaderQuerySqlErrorCodes;
import com.demo.reader.querysql.json.dto.GetPlanetResponseDto;

/**
 * Responsible for handling the processing of the Response from the Search Performed
 * 
 * @author SULWAYJO
 *
 */

public class RetrieveResponseHandler {
	
	private final Logger logger = Logger.getLogger(RetrieveResponseHandler.class);
	
	@Autowired
	PropertyHelper errorPropertyHelper;
	
	
	/**
	 * Handles the JAVA to JSON processing of getPlanetResponseDto
	 * 
	 * @param getPlanetResponseDto
	 * @return
	 * @throws ConversionException
	 */
	public String handleResponse(GetPlanetResponseDto getPlanetResponseDto) throws ConversionException{
		String jsonString = "{\"errorMessage\":\"No Records found\"}";
		try{			
			 if(getPlanetResponseDto != null){
				 logger.info("Performing jackson response conversion (Java Object to JSON String)");
				 ObjectMapper mapper = new ObjectMapper();
				 jsonString = mapper.writeValueAsString(getPlanetResponseDto);
				 logger.info("JSON : " + jsonString);
			 }
		}catch(Exception ex){
			logger.error(errorPropertyHelper.getPropertyAndReplaceTemplates("ConversionExceptionMessage", ex.getMessage()));
			throw new CommonRuntimeException(MsgReaderQuerySqlErrorCodes.CONVERSION_EXCEPTION, ex, errorPropertyHelper.getPropertyAndReplaceTemplates("ConversionExceptionMessage", ex.getMessage()));
			
		}
		return jsonString;
	}
}
