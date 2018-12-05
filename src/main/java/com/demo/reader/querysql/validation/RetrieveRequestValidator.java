package com.demo.reader.querysql.validation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.reader.querysql.json.dto.GetPlanetRequestDto;
import com.demo.properties.PropertyHelper;
import com.demo.reader.querysql.exception.ValidationException;

/**
 * Responsible for validating Search Term Input Values
 * 
 * @author SULWAYJO
 *
 */
public class RetrieveRequestValidator {
	
	private final Logger logger = Logger.getLogger(RetrieveRequestValidator.class);

	@Autowired
	PropertyHelper errorPropertyHelper;
	
	/**
	 * Performs validation of specific retrieve Attributes. A Validation Exception
	 * is thrown in the event validation fails
	 * 
	 * @param GetPlanetRequestDto
	 * @throws ValidationException
	 */
	public void validate(GetPlanetRequestDto getPlanetRequestDto) throws ValidationException{
		logger.debug("Performing RetrieveRequestValidation (GetTeamRequestDto)");
		
		if(getPlanetRequestDto != null){
			if(getPlanetRequestDto.getPlanetName() != null){
				validatePlanetName(getPlanetRequestDto.getPlanetName());
			}else{
				logAndRaise(errorPropertyHelper.getProperty("GetPlanetRequest_NoData"));
			}
		}
	}
	
	
	/**
	 * Validate the planet name (255 alphanumeric)
	 * 
	 * @param teamName
	 * @throws ValidationException
	 */
	private void validatePlanetName(String planetName) throws ValidationException{
		if(planetName.length() > 255){
			logAndRaise(errorPropertyHelper.getProperty("GetPlanetRequest_PlanetNameRestrictedSize"));
		}
	}
	
	/**
	 * Log and raise
	 * @param errorMessage
	 * @throws ValidationException
	 */
	private void logAndRaise(String errorMessage) throws ValidationException{
		logger.error(errorMessage);
		throw new ValidationException(errorMessage);
	}

	/**
	 * Get ErrorProperties
	 * @return
	 */
	public PropertyHelper getProperties() {
		return errorPropertyHelper;
	}

	/**
	 * Set ErrorProperties
	 * 
	 * @param properties
	 */
	public void setProperties(PropertyHelper properties) {
		this.errorPropertyHelper = properties;
	}
	
	
}
