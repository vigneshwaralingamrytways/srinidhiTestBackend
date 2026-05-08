package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActiveStatusConvertor implements AttributeConverter<ActiveStatus, Integer>{
	
	@Override
    public Integer convertToDatabaseColumn(ActiveStatus Categories) {
        return Categories.getcatValue();
    }

	@Override
	public ActiveStatus convertToEntityAttribute(Integer dbData) {
		// TODO Auto-generated method stub
		 return ActiveStatus.fromcatValue(dbData);
	}
	
}
