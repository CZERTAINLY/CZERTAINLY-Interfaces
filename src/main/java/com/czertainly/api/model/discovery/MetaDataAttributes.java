package com.czertainly.api.model.discovery;

import java.io.Serializable;

public class MetaDataAttributes {
	
	private String key;
	
	/** Value of metaData, has to be serializable **/
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
