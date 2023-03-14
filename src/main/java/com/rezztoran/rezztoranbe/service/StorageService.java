package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.model.StoredFile;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  String upload(MultipartFile multipartFile);

  StoredFile download(String id);
}
