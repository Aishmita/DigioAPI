package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMetaData {
    private String ID;
    private boolean is_agreement;
    private String agreement_type;
    private String agreement_status;
    private String file_name;
    private Timestamp createdAt;
    private boolean self_signed;
    private String self_sign_type;
    private int no_of_pages;
    private List<SigningParty> signing_parties;
    private String channel;
    private SignRequestDetails sign_request_details;
}
