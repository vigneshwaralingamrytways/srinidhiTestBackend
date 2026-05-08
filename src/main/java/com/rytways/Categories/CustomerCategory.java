package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum CustomerCategory {
	Select(0),Domestic(1),Export(2);

    private Integer catValue;
    
    
    private CustomerCategory(Integer catValue) {
        this.catValue = catValue;
    }
 
    public Integer getcatValue() {
        return catValue;
    }
 
    public static CustomerCategory fromcatValue(int catValue) {
        switch (catValue) {
        case 1:
            return CustomerCategory.Domestic;
        case 2:
            return CustomerCategory.Export;
        default:
            return CustomerCategory.Select;
        }
    }
    
   
}
