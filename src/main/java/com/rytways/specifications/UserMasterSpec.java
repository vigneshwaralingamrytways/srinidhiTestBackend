package com.rytways.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.rytways.model.Users;

public class UserMasterSpec {
	
	
	
	public static Specification<Users> userNameLike(String userName) {
		 return (root, query, builder) -> 
		 userName == null || userName == "" ? 
           builder.conjunction() :
           builder.like(builder.lower(root.get("userName")),
        	          "%" + userName.toLowerCase() + "%");
	    }
	
    public static Specification<Users> roleIdEqual(Integer roleId) {
        return (root, query, builder) -> 
        roleId == null || roleId == 0 ? 
                builder.conjunction() :
                builder.equal(root.get("roleId"), roleId);
    }
    

}
