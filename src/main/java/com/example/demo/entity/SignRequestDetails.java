package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignRequestDetails {
    private String name;
    private String identifier;
    private Timestamp requested_on;
    private Timestamp expire_on;
    private String requester_type;

}
