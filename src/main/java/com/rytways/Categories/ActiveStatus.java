package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum ActiveStatus {
	Select(0),Yes(1),No(2);

    private Integer catValue;
        
    
    private ActiveStatus(Integer catValue) {
    	
    	if(catValue==null){
    		this.catValue = 0;
    	}else{
        this.catValue = catValue;
    	}
    }
 
    public Integer getcatValue() {
        return catValue;
    }
 
    public static ActiveStatus fromcatValue(int catValue) {
        switch (catValue) {
        case 1:
            return ActiveStatus.Yes;
        case 2:
            return ActiveStatus.No;
        default:
            return ActiveStatus.Select;
        }
    }
}
