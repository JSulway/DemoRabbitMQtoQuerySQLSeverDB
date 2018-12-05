package com.demo.reader.querysql.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

/**
 * GetPlanetQueryDto is that populated when the named native query defined is executed.
 *  
 * 
 * @author SULWAYJO
 *
 */
@Entity
@NamedStoredProcedureQuery(	
		name="namedStoredProcedureRetrievePlanetQuery",
		procedureName="demo.namedStoredProcedureRetrievePlanetQuery",
		parameters = { 
			@StoredProcedureParameter(name="planetName", mode = ParameterMode.IN, type = String.class)
		},
		resultClasses = GetPlanetQueryDto.class)
public class GetPlanetQueryDto {

	@Id
	@Column(name="PLANET_ID")
	private int planetId;
	
	@Column(name="PLANET_NAME")
	private String planetName;
	
	@Column(name="PLANET_CREATED_DATE")
	private Date planetCreatedDate;
	
	@Column(name="UPDATED_DATETIME")
	private Date updateDateTime;
	
	@Column(name="CLASS_TYPE")
	private String classType;
	
	@Column(name="ADVANCEMENT_LEVEL")
	private String advancementLevel;
	
	@Column(name="PRIMARY_SPECIES")
	private String primarySpecies;
	
	@Column(name="POPULATION")
	private int population;
	
	@Column(name="LANGUAGE")
	private String language;
	
	@Column(name="CLIMATE")
	private String climate;
	
	@Column(name="NOTES")
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

	public Date getPlanetCreatedDate() {
		return planetCreatedDate;
	}

	public void setPlanetCreatedDate(Date planetCreatedDate) {
		this.planetCreatedDate = planetCreatedDate;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
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
