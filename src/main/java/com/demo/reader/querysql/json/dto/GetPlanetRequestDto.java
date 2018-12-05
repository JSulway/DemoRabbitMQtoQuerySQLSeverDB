package com.demo.reader.querysql.json.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class representing the GetPlanetRequestDto Structure used for Jackson JSON mapping.
 * 
 * @author SULWAYJO
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPlanetRequestDto {
	
	private String planetName;
	
	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		stringBuilder.append("################ Get Planet Request : request values : ");
		stringBuilder.append("\n");
		
		if(planetName != null){	
			stringBuilder.append(">> planetName : " + planetName);
			stringBuilder.append("\n");
		}
		
		stringBuilder.append("\n\n");
		
		return stringBuilder.toString();
	}
}
