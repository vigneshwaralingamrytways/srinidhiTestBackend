package com.rytways.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rytways.model.RefreshToken;







public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
    
    
    @Query(value = "SELECT * FROM refresh_token where user_id=:userId and updated_on="
    		+ "(select max(updated_on) from refresh_token where user_id=:userId)",nativeQuery = true)
    Optional<RefreshToken> findByuserId(@Param("userId")int userId);
   
    /*@Query(value="select id from refresh_token WHERE user_id = :userId",nativeQuery=true)
    List<Integer> findByuserId(@Param("userId")int userId);
   */
   
}