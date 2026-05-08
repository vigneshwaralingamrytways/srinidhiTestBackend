package com.rytways.service;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.rytways.model.ClientUsers;
import com.rytways.model.RefreshToken;
import com.rytways.model.Users;
//import com.rytways.repository.ClientUserRepository;
import com.rytways.repository.RefreshTokenRepository;
import com.rytways.repository.UserRepository;




@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userInfoRepository;
    
//    @Autowired
//    private ClientUserRepository clientUserRepository;

//    public RefreshToken createRefreshToken(String username) {
//        RefreshToken refreshToken = RefreshToken.builder()
//                .userInfo(userInfoRepository.findByUserName(username).get())
//                .token(UUID.randomUUID().toString())
//              //  .expiryDate(Instant.now().plusMillis(600000))
//                .expiryDate(Instant.now().plusMillis(1*60*1000))
//                .build();
//        return refreshTokenRepository.save(refreshToken);
//    }
    
    public RefreshToken createRefreshToken(String username) {
   	 Optional<Users> user = userInfoRepository.findByUserName(username);
        if (user.isPresent()) {
            return createForUser(user.get());
        
         }
        	else {
                throw new RuntimeException("User or Client not found with username: " + username);
            }
        }
        
    

        private RefreshToken createForUser(Users user) {
       	 RefreshToken refreshToken = RefreshToken.builder()
                   .userInfo(user)
                   .token(UUID.randomUUID().toString())
                   .expiryDate(Instant.now().plusMillis(1 * 60 * 1000))
                   .build();
           return refreshTokenRepository.save(refreshToken);
       }

       
       
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
    
    
   
}