package com.rytways.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rytways.Categories.ActiveStatus;
import com.rytways.dto.LoadOptionsDto;
import com.rytways.model.Categories;
import com.rytways.model.UnitMaster;

public interface CategoriesRepo extends JpaRepository<Categories, Long>{
	
	@Query("select new com.rytways.dto.LoadOptionsDto(cat.categoryId as value, cat.categoryName as label)"
			+ " from Categories cat where cat.CategoryType=?1 and toShow=?2")
	List<LoadOptionsDto> findLoadOptions(String categoryType,ActiveStatus status);

}
