package com.rytways.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.Roles;

public class RoleSpec {

	
	
	public static Specification<Roles> roleNameLike(String roleName) {
		 return (root, query, builder) -> 
		 roleName == null || roleName == "" ? 
          builder.conjunction() :
          builder.like(builder.lower(root.get("roleName")),
       	          "%" + roleName.toLowerCase() + "%");
	    }
	
	
}
