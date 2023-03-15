package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.model.StoredFile;
import org.springframework.web.multipart.MultipartFile;

/** The interface Storage service. */
public interface StorageService {
  /**
   * Upload string.
   *
   * @param multipartFile the multipart file
   * @return the string
   */
  String upload(MultipartFile multipartFile);

  /**
   * Download stored file.
   *
   * @param id the id
   * @return the stored file
   */
  StoredFile download(String id);
}
