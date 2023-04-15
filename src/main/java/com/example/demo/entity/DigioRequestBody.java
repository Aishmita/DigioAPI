package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigioRequestBody {
    private List<Signer> signers;
    private int expire_in_days;
    private String display_on_page;
    private String file_name;
    private String file_data;
}
