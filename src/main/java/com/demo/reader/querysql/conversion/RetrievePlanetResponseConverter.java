package com.demo.reader.querysql.conversion;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.common.CommonConstant;
import com.demo.properties.PropertyHelper;
import com.demo.reader.querysql.domain.GetPlanetQueryDto;
import com.demo.reader.querysql.exception.ConversionException;
import com.demo.reader.querysql.json.dto.GetPlanetResponseDto;

/**
 * Conversion logic to take the object structure returned from the Query and
 * map it to the retrieve Response structure, which will subsequently be converted to JSON when sent on.
 *  
 * @author SULWAYJO
 *
 */
public class RetrievePlanetResponseConverter {
	
	@Autowired
	PropertyHelper errorPropertyHelper;

	/**
	 * 
	 * Conversion method which will map to a JSON response DTO class necessary when specific formatting of
	 * data is required from the API
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public GetPlanetResponseDto convert(GetPlanetQueryDto retrievePlanetQueryDto) throws ConversionException{
		GetPlanetResponseDto getPlanetResponseDto = null;
		
		if(retrievePlanetQueryDto != null){
			try{
				getPlanetResponseDto = new GetPlanetResponseDto();
				getPlanetResponseDto.setPlanetId(retrievePlanetQueryDto.getPlanetId());
				getPlanetResponseDto.setPlanetName(retrievePlanetQueryDto.getPlanetName());
				getPlanetResponseDto.setPlanetCreatedDate(parseDate(retrievePlanetQueryDto.getPlanetCreatedDate()));
				getPlanetResponseDto.setUpdateDateTime(parseDate(retrievePlanetQueryDto.getUpdateDateTime()));
				getPlanetResponseDto.setClassType(retrievePlanetQueryDto.getClassType());
				getPlanetResponseDto.setAdvancementLevel(retrievePlanetQueryDto.getAdvancementLevel());
				getPlanetResponseDto.setPrimarySpecies(retrievePlanetQueryDto.getPrimarySpecies());
				getPlanetResponseDto.setPopulation(retrievePlanetQueryDto.getPopulation());
				getPlanetResponseDto.setLanguage(retrievePlanetQueryDto.getLanguage());
				getPlanetResponseDto.setClimate(retrievePlanetQueryDto.getClimate());
				getPlanetResponseDto.setNotes(retrievePlanetQueryDto.getNotes());	
				
			}catch(Exception e){
				throw new ConversionException(errorPropertyHelper.getPropertyAndReplaceTemplates("ConversionExceptionMessage", e.getMessage()));
			}
		}
		
		return getPlanetResponseDto;
	}
	
	/**
	 * Parse Date to the expected format
	 * 
	 * @param createdDate
	 * @return
	 */
	private String parseDate(Date createdDate){
		String value = "";
		if(createdDate != null){
			value = new SimpleDateFormat(CommonConstant.DATE_TIME_FORMAT_DDMMYYYYHHMMSS).format(createdDate);
		}
		return value;
	}
}
