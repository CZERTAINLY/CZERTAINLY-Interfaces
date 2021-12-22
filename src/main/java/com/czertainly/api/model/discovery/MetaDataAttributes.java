package com.czertainly.api.model.discovery;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class MetaDataAttributes {

	@Schema(description = "Meta data key", required = true)
	private String key;
	
	/** Value of metaData, has to be serializable **/
	@Schema(description = "Meta data value", required = true)
    private Serializable value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public MetaDataAttributes() {
		super();
	}

	public MetaDataAttributes(String key, Serializable value) {
		super();
		this.key = key;
		this.value = value;
	}
    
    
}
