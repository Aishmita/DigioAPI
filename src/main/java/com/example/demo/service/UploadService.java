package com.example.demo.service;

import com.example.demo.entity.DigioRequestBody;
import com.example.demo.entity.DocumentMetaData;
import com.example.demo.entity.UploadDocument;
import com.example.demo.util.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@Component
@Slf4j
public class UploadService {



    RestTemplate restTemplate = new RestTemplate();
    public boolean uploadDocument(UploadDocument uploadDocument, MultipartFile pdfFile) throws IOException {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("authorization","Basic " + getAuthToken(AppConstants.ClientID, AppConstants.ClientSecret));
        httpHeaders.set("content-type","application/json");
        httpHeaders.set("Accept","application/json");
        String url = AppConstants.BaseURL+"/v2/client/document/uploadpdf";
        DigioRequestBody body = new DigioRequestBody(uploadDocument.getSigners(), uploadDocument.getExpire_in_days(), uploadDocument.getDisplay_on_page(), pdfFile.getOriginalFilename(), getFileEncode(pdfFile));
        HttpEntity<DigioRequestBody> digioRequestBodyHttpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<?> response = restTemplate.postForEntity(url, digioRequestBodyHttpEntity, DigioRequestBody.class);
        log.info("Response: {}", response.getBody());
        if(response.getStatusCode().is2xxSuccessful()){
            return true;
        }
        else
            return false;
    }
    private String getAuthToken(String clientId, String clientSecret) {
        return new String(Base64.encodeBase64((clientId + ":" + clientSecret).getBytes()));
    }

    private String getFileEncode(MultipartFile file) throws IOException {
        byte[] inFileBytes = file.getBytes();
        byte[] encoded = Base64.encodeBase64(inFileBytes);
        return new String(encoded);
    }


    public MultipartFile downloadDocumentByID(String documentID){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("authorization","Basic " + getAuthToken(AppConstants.ClientID, AppConstants.ClientSecret));

        String url = AppConstants.BaseURL+"/v2/client/document/download?document_id={documentId}";
        String url1 = url.replace("{documentId}", documentID);


        try{
            ResponseEntity<?> response = restTemplate.exchange(url1, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                log.info("Response: {}", response.getBody());
                return (MultipartFile) response.getBody();
            }
        }catch (HttpStatusCodeException exception){
            return null;
        }
        return null;
    }

    public DocumentMetaData getDocumentDataByID(String documentID){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("authorization","Basic " + getAuthToken(AppConstants.ClientID, AppConstants.ClientSecret));

        String url = AppConstants.BaseURL+"/v2/client/document/{documentId}";
        String url1 = url.replace("{documentId}", documentID);

        try{
            ResponseEntity<?> response = restTemplate.exchange(url1, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                log.info("Response: {}", response.getBody());
                return (DocumentMetaData) response.getBody();
            }
        }catch (HttpStatusCodeException exception){
            return null;
        }
        return null;
    }
}
