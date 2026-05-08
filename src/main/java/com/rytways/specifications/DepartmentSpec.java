package com.rytways.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.DepartmentMaster;

public class DepartmentSpec {
	
	
	
	public static Specification<DepartmentMaster> departmentNameLike(String departmentName) {
		 return (root, query, builder) -> 
		 departmentName == null || departmentName == "" ? 
          builder.conjunction() :
          builder.like(builder.lower(root.get("departmentName")),
       	          "%" + departmentName.toLowerCase() + "%");
	    }
	
	
	public static Specification<DepartmentMaster> departmentHeadLike(String departmentHead) {
		 return (root, query, builder) -> 
		 departmentHead == null || departmentHead == "" ? 
         builder.conjunction() :
         builder.like(builder.lower(root.get("departmentHead")),
      	          "%" + departmentHead.toLowerCase() + "%");
	    }
	
	public static Specification<DepartmentMaster> assDepartmentHeadLike(String assDepartmentHead) {
		 return (root, query, builder) -> 
		 assDepartmentHead == null || assDepartmentHead == "" ? 
         builder.conjunction() :
         builder.like(builder.lower(root.get("assDepartmentHead")),
      	          "%" + assDepartmentHead.toLowerCase() + "%");
	    }

}
