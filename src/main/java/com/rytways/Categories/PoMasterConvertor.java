package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PoMasterConvertor implements AttributeConverter<PoMasterStatus, Integer>{
	
	@Override
    public Integer convertToDatabaseColumn(PoMasterStatus Categories) {
        return Categories.getcatValue();
    }

	@Override
	public PoMasterStatus convertToEntityAttribute(Integer dbData) {
		// TODO Auto-generated method stub
		 return PoMasterStatus.fromcatValue(dbData);
	}
	
}
