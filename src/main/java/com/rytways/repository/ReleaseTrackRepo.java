package com.rytways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rytways.model.ReleaseTrack;

@Repository
public interface ReleaseTrackRepo extends JpaRepository<ReleaseTrack, Long>{

}
