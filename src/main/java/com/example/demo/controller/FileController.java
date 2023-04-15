package com.example.demo.controller;

import com.example.demo.entity.DocumentMetaData;
import com.example.demo.entity.UploadDocument;
import com.example.demo.service.UploadService;
import com.example.demo.util.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = {AppConstants.BASE_URL})
public class FileController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UploadService uploadService;

    @PostMapping
    public ResponseEntity<?> uploadDocument(@RequestParam("file")MultipartFile file, @RequestParam("uploadRequest") String uploadDocument) throws IOException {
        log.info("Doc: {}", uploadDocument);
        log.info("File: {}", file.getContentType());

        if(!file.getContentType().equals("application/pdf")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please share a pdf file");
        }
        UploadDocument pdfFile = null;
        try {
            pdfFile = objectMapper.readValue(uploadDocument, UploadDocument.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request!");
        }

        boolean isUpload = uploadService.uploadDocument(pdfFile, file);
        if(isUpload) return ResponseEntity.status(HttpStatus.OK).body("Document Uploaded Successfully!");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some Issue while uploading the document ");
    }

    @GetMapping(value = "/{documentID}")
    public ResponseEntity<?> getDocument(@PathVariable String documentID) throws IOException {
        DocumentMetaData metaData = uploadService.getDocumentDataByID(documentID);
        if(Objects.nonNull(metaData)){
            return ResponseEntity.status(HttpStatus.OK).body(metaData);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document ID not found: " + documentID);
        }
    }

    @GetMapping(value = "/downloadDocument/{documentID}")
    public ResponseEntity<?> downloadDocument(@PathVariable String documentID) throws IOException {
        MultipartFile file = uploadService.downloadDocumentByID(documentID);
        if(Objects.nonNull(file)){
            return ResponseEntity.status(HttpStatus.OK).body(file);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found for : " + documentID);
        }
    }
}
