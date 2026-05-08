package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomerCategoryConvertor implements AttributeConverter<CustomerCategory, Integer>{
	
	@Override
    public Integer convertToDatabaseColumn(CustomerCategory Categories) {
        return Categories.getcatValue();
    }

	@Override
	public CustomerCategory convertToEntityAttribute(Integer dbData) {
		// TODO Auto-generated method stub
		 return CustomerCategory.fromcatValue(dbData);
	}
	
}
