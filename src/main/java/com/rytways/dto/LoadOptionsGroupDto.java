package com.rytways.dto;


import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoadOptionsGroupDto {
	 private String value;
	    private String label;
	    private String groupName; 

	    

	   

	    public LoadOptionsGroupDto(String value, String label, String groupName) {
	        this.value = value;
	        this.label = label;
	        this.groupName = groupName;
	    }
}
