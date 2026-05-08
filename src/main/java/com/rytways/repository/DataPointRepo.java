package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.model.DataPointModel;

@Repository
public interface DataPointRepo extends JpaRepository<DataPointModel, Long>, JpaSpecificationExecutor<DataPointModel> {

	List<DataPointModel> findByDataIdAndType(Long dataId, String type);

	

	@Query("SELECT DISTINCT d.functionId FROM DataPointModel d WHERE d.type = :type AND d.functionId IS NOT NULL")
	List<Long> findDistinctFunctionIdsByType(@Param("type") String type);

	
	@Query("SELECT DISTINCT d.activityId FROM DataPointModel d WHERE d.type = :type AND d.activityId IS NOT NULL")
	List<Long> findDistinctActivityIdsByDataIdAndType(@Param("type") String type);



	 @Query("SELECT DISTINCT dp.processId FROM DataPointModel dp WHERE dp.dataId = :dataId AND dp.type = :type")
    List<Long> findDistinctProcessIdsByDataIdAndType(@Param("dataId") Long dataId, @Param("type") String type);



	boolean existsByDataIdAndProcessIdAndType(Long dataId, Long processId, String type);



	boolean existsByDataIdAndFunctionIdAndType(Long dataId, Long functionId, String type);



	boolean existsByDataIdAndActivityIdAndType(Long dataId, Long activityId, String type);



	List<DataPointModel> findByDataIdAndTypeAndInfoType(Long dataId, String type, String infoType);



	List<DataPointModel> findByDataIdAndTypeAndInfoTypeAndRefReqDataPointId(Long dataId, String type, String infoType,
			Long long1);



	List<DataPointModel> findByProcessIdAndType(Long dataId, String type);



	List<DataPointModel> findByFunctionIdAndType(Long dataId, String type);



	@Modifying
	@Query(value = "UPDATE data_point_model SET connected = 'YES' WHERE data_point_id = :id AND (connected IS NULL OR connected <> 'YES')", nativeQuery = true)
	int updateConnectedIfNotYes(@Param("id") Long id);



	@Modifying
@Query(
  value = "UPDATE data_point_model p " +
          "SET p.connected = CASE " +
          "    WHEN EXISTS (SELECT 1 FROM data_point_model c WHERE c.ref_req_data_point_id = :refReqDataPointId) " +
          "    THEN 'YES' " +
          "    ELSE 'NO' " +
          "END " +
          "WHERE p.data_point_id = :refReqDataPointId",
  nativeQuery = true
)
void updateConnectedStatusAfterDelete(@Param("refReqDataPointId") Long refReqDataPointId);




	

}
