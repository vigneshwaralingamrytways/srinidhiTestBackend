package com.rytways.Categories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum PoMasterStatus {
	Select(0),Awaiting_Approval(1),Approved(2),In_Production(3),Closed(4),Rejected(5),Created(6),Purchase_Approved(7),
	Finance_Approved(8),HO_Approved(9),Authorised(10),Cancelled(11),
	Partially_Received(12),Yet_To_Receive(13),Fully_Invoiced(14),Draft_Po(15),Commercial_And_Purchase_Approved(16),CEO_Approved(17);

    private Integer catValue;
        
    
    private PoMasterStatus(Integer catValue) {
    	
    	if(catValue==null){	
    		this.catValue = 0;
    	}else{
        this.catValue = catValue;
    	}
    }
 
    public Integer getcatValue() {
        return catValue;
    }
 
    public static PoMasterStatus fromcatValue(int catValue) {
        switch (catValue) {
        case 1:
            return PoMasterStatus.Awaiting_Approval;
        case 2:
            return PoMasterStatus.Approved;
        case 4:
            return PoMasterStatus.Closed;
        case 5:
            return PoMasterStatus.Rejected;
        case 6:
            return PoMasterStatus.Created;
        case 7:
            return PoMasterStatus.Purchase_Approved;
        case 8:
            return PoMasterStatus.Finance_Approved;
        case 9:
            return PoMasterStatus.HO_Approved;
        case 10:
            return PoMasterStatus.Authorised;
        case 11:
            return PoMasterStatus.Cancelled;
        case 12:
            return PoMasterStatus.Partially_Received;
        case 13:
            return PoMasterStatus.Yet_To_Receive;
        case 14:
            return PoMasterStatus.Fully_Invoiced;
        case 15:
            return PoMasterStatus.Draft_Po;
        case 16 :
        	return PoMasterStatus.Commercial_And_Purchase_Approved;
        case 17 :
        	return PoMasterStatus.CEO_Approved;
         default:
            return PoMasterStatus.Select;
        }
    }
    
   
}
