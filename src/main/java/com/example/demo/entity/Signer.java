package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signer {
    private String identifier;
    private String name;
    private String reason;
}
