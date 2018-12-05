package com.demo.reader.querysql.json.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * GetPlanetResponseDto maps to the required JSON response string on JAVA to JSON conversion
 * 
 * @author SULWAYJO
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPlanetResponseDto {

	private int planetId;
	private String planetName;
	private String planetCreatedDate;
	private String updateDateTime;
	private String classType;
	private String advancementLevel;
	private String primarySpecies;
	private int population;
	private String language;
	private String climate;
	private String notes;
	
	public int getPlanetId() {
		return planetId;
	}
	public void setPlanetId(int planetId) {
		this.planetId = planetId;
	}
	public String getPlanetName() {
		return planetName;
	}
	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}
	public String getPlanetCreatedDate() {
		return planetCreatedDate;
	}
	public void setPlanetCreatedDate(String planetCreatedDate) {
		this.planetCreatedDate = planetCreatedDate;
	}
	public String getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getAdvancementLevel() {
		return advancementLevel;
	}
	public void setAdvancementLevel(String advancementLevel) {
		this.advancementLevel = advancementLevel;
	}
	public String getPrimarySpecies() {
		return primarySpecies;
	}
	public void setPrimarySpecies(String primarySpecies) {
		this.primarySpecies = primarySpecies;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
