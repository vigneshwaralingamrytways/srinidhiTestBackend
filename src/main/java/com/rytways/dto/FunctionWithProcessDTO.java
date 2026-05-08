package com.rytways.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionWithProcessDTO {
    private Long functionId;
    private String functionName;
    private String functionPath;
    private Long processId;
    private ProcessInfo process;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProcessInfo {
        private String processName;
        private String processPath;
        private String description;
    }
}
