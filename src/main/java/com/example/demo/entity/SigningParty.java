package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigningParty {
    private String name;
    private String identifier;
    private String status;
    private String type;
    private String signature_type;
    private String reason;

}
