
package com.rytways.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rytways.model.ApprovalsHistory;
import com.rytways.model.Users;

import lombok.Data;

@Data
public class AppHistDto {
    private ApprovalsHistory appHist;

    public AppHistDto(ApprovalsHistory hist, Users user) {
        this.appHist = hist;
         this.appHist.setApprovalPerson(user != null ? user : new Users());
    }

    // Getters and Setters
}