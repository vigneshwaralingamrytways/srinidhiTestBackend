package com.rytways.specifications;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.DispatchPass;
import com.rytways.model.VisitorsPass;

public class VisitorsPassSpecs {
//	 public static Specification<VisitorsPass> fromDate(LocalDateTime purchaseDate) {
//	        return (root, query, builder) ->
//	                purchaseDate == null ?
//	                        builder.conjunction() :
//	                        builder.greaterThanOrEqualTo(root.get("visitingDate"), purchaseDate);
//	    }
	public static Specification<VisitorsPass> fromDate(LocalDateTime purchaseDate) {
	    return (root, query, builder) -> {
	        if (purchaseDate == null) {
	            return builder.conjunction();
	        }

	        return builder.or(
	            builder.and(
	                builder.isNotNull(root.get("visitingDate")),
	                builder.greaterThanOrEqualTo(root.get("visitingDate"), purchaseDate)
	            ),
	            builder.and(
	                builder.isNull(root.get("visitingDate")),
	                builder.greaterThanOrEqualTo(root.get("entryDate"), purchaseDate.toLocalDate())
	            )
	        );
	    };
	}

//	    public static Specification<VisitorsPass> tillDate(LocalDateTime purchaseDate) {
//	        return (root, query, builder) ->
//	                purchaseDate == null ?
//	                        builder.conjunction() :
//	                        builder.lessThanOrEqualTo(root.get("visitingDate"), purchaseDate);
//	    }
	public static Specification<VisitorsPass> tillDate(LocalDateTime purchaseDate) {
	    return (root, query, builder) -> {
	        if (purchaseDate == null) {
	            return builder.conjunction();
	        }

	        return builder.or(
	            builder.and(
	                builder.isNotNull(root.get("visitingDate")),
	                builder.lessThanOrEqualTo(root.get("visitingDate"), purchaseDate)
	            ),
	            builder.and(
	                builder.isNull(root.get("visitingDate")),
	                builder.lessThanOrEqualTo(root.get("entryDate"), purchaseDate.toLocalDate())
	            )
	        );
	    };
	}


	    

	    public static Specification<DispatchPass> gateContains(List<Integer> units) {
	        return (root, query, builder) ->
	                root.get("gateId").in(units);
	    }

	    public static Specification<VisitorsPass> department(String cusName) {
	        return (root, query, builder) ->
	        cusName == null ?
	                        builder.conjunction() :
	                        builder.like(root.get("department"), cusName);
	    }
	    
	    public static Specification<VisitorsPass> cusNameEquals(String cusName) {
	        return (root, query, builder) ->
	        cusName == null ?
	                        builder.conjunction() :
	                        builder.like(root.get("createBy"), cusName);
	    }
}
