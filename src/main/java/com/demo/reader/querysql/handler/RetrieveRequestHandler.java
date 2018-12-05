package com.demo.reader.querysql.handler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.reader.querysql.domain.GetPlanetQueryDto;

import com.demo.reader.querysql.exception.ConversionException;
import com.demo.reader.querysql.exception.MsgReaderQuerySqlErrorCodes;
import com.demo.reader.querysql.exception.ValidationException;
import com.demo.reader.querysql.json.dto.GetPlanetRequestDto;
import com.demo.reader.querysql.json.dto.GetPlanetResponseDto;
import com.demo.error.CommonRuntimeException;
import com.demo.properties.PropertyHelper;
import com.demo.reader.querysql.conversion.RetrievePlanetResponseConverter;
import com.demo.reader.querysql.validation.RetrieveRequestValidator;


/**
 * 
 * A request handler Orchestrating processing required prior to the message being sent on to SQL Server
 * 
 * @author SULWAYJO
 *
 */

public class RetrieveRequestHandler {	
	
	private final Logger logger = Logger.getLogger(RetrieveRequestHandler.class);
		
	@Autowired
	PropertyHelper errorPropertyHelper;

	@Autowired
	private RetrieveRequestValidator retrieveRequestValidator;
	
	@PersistenceContext
    public EntityManager em;
	
	/**
	 * Handles a GetTeamRequestDto request
	 * 
	 * @param retrieveRequest
	 * @return
	 * @throws ValidationException
	 * @throws ConversionException
	 * @throws SQLException
	 */
	public GetPlanetResponseDto handleRequest(GetPlanetRequestDto retrieveRequest) {
		logger.debug("...entering retrieveRequestHandler:handleRequest (GetPlanetRequestDto)");
		GetPlanetResponseDto getPlanetResponseDto = null;
				
		try{
			retrieveRequestValidator.validate(retrieveRequest);
		}catch(ValidationException vex){
			throw new CommonRuntimeException(MsgReaderQuerySqlErrorCodes.VALIDATION_EXCEPTION, vex, vex.getMessage() );
		}
		
		logger.info("Creating namedStoredProcedureRetrievePlanetQuery");
		logger.info("Setting planetName as " + retrieveRequest.getPlanetName() + " and executing query");
		StoredProcedureQuery retrievePlanetQuery = this.em.createNamedStoredProcedureQuery("namedStoredProcedureRetrievePlanetQuery");
		retrievePlanetQuery.setParameter("planetName", retrieveRequest.getPlanetName());		
		
		@SuppressWarnings("unchecked")
		List<GetPlanetQueryDto> retrievePlanetResultList = (List<GetPlanetQueryDto>)retrievePlanetQuery.getResultList();
				
		if(retrievePlanetResultList != null && !retrievePlanetResultList.isEmpty()){
			GetPlanetQueryDto planetQueryDto = (GetPlanetQueryDto)retrievePlanetResultList.get(0);
			
			RetrievePlanetResponseConverter retrievePlanetResponseConverter = new RetrievePlanetResponseConverter();
			
			try{
				getPlanetResponseDto = retrievePlanetResponseConverter.convert(planetQueryDto);
			}catch(Exception e){
				throw new CommonRuntimeException(MsgReaderQuerySqlErrorCodes.CONVERSION_EXCEPTION, e, e.getMessage());
			}
		}
		
		logger.debug("...exiting retrieveRequestHandler:handleRequest (GetPlanetRequestDto)");
		return getPlanetResponseDto;
	}
}
