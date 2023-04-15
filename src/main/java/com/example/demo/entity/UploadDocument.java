package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocument {
    private List<Signer> signers;
    private int expire_in_days;
    private String display_on_page;
    private Boolean notify_signers;
}
