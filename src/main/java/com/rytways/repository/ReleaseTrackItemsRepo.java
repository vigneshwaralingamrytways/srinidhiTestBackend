package com.rytways.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rytways.model.ReleaseTrackItems;

@Repository
public interface ReleaseTrackItemsRepo extends JpaRepository<ReleaseTrackItems, Long>{
	
	@Query("SELECT r FROM ReleaseTrackItems r WHERE r.releaseTrackId = :trackId ORDER BY COALESCE(r.displaySeqNo, 0)")
	List<ReleaseTrackItems> findByReleaseTrackIdSorted(@Param("trackId") Long trackId);


	List<ReleaseTrackItems> findByReleaseTrackId(Long releaseTrackId);

	boolean existsByReleaseTrackId(Long trackId);

	@Query("SELECT r FROM ReleaseTrackItems r " +
       "LEFT JOIN FETCH r.activity a " +
       "WHERE r.releaseTrackId = :releaseTrackId " +
       "ORDER BY a.displaySeqNo ASC")
List<ReleaseTrackItems> findByReleaseTrackIdOrderByDisplaySeq(@Param("releaseTrackId") Long releaseTrackId);

	List<ReleaseTrackItems> findByReleaseTrackIdOrderByDisplaySeqNoAsc(Long releaseTrackId);


}
