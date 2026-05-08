package com.rytways.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDTO {
    private Integer functionId;
    private String functionName;
    private String functionPath;
}