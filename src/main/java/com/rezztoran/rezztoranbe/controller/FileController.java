package com.rezztoran.rezztoranbe.controller;

import com.rezztoran.rezztoranbe.model.StoredFile;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import com.rezztoran.rezztoranbe.service.StorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** The type File controller. */
@RestController
@RequestMapping("/api/file")
public class FileController {

  private final StorageService storageService;

  /**
   * Instantiates a new File controller.
   *
   * @param storageService the storage service
   */
  public FileController(StorageService storageService) {
    this.storageService = storageService;
  }

  /**
   * Upload response entity.
   *
   * @param file the file
   * @return the response entity
   */
  @PostMapping(value = "/upload", produces = "application/json")
  public ResponseEntity<ApiResponse<Object>> upload(@RequestParam("file") MultipartFile file) {
    String key = storageService.upload(file);
    return ApiResponse.builder().data(key).build();
  }

  /**
   * Download response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @GetMapping("/download/{id}")
  public ResponseEntity<Resource> download(@PathVariable String id) {
    StoredFile storedFile = storageService.download(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + storedFile.getFileName())
        .contentLength(storedFile.getContentLength())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(storedFile.getInputStream()));
  }
}
