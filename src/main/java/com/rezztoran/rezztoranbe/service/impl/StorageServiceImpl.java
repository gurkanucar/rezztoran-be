package com.rezztoran.rezztoranbe.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.rezztoran.rezztoranbe.model.StoredFile;
import com.rezztoran.rezztoranbe.service.StorageService;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/** The type Storage service. */
@Service
public class StorageServiceImpl implements StorageService {

  private final AmazonS3 amazonS3;
  private final String bucketName;

  /**
   * Instantiates a new Storage service.
   *
   * @param amazonS3 the amazon s 3
   * @param bucketName the bucket name
   */
  public StorageServiceImpl(AmazonS3 amazonS3, @Value("${aws.s3.bucket-name}") String bucketName) {
    this.amazonS3 = amazonS3;
    this.bucketName = bucketName;

    initializeBucket();
  }

  @SneakyThrows
  @Override
  public String upload(MultipartFile multipartFile) {
    String filenameExtension =
        StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    String key = RandomStringUtils.randomAlphanumeric(50) + "." + filenameExtension;
    amazonS3.putObject(
        bucketName, key, multipartFile.getInputStream(), extractObjectMetadata(multipartFile));

    return key;
  }

  @Override
  public StoredFile download(String id) {
    S3Object s3Object = amazonS3.getObject(bucketName, id);
    Long contentLength = s3Object.getObjectMetadata().getContentLength();
    return StoredFile.builder()
        .id(id)
        .fileName(id)
        .contentLength(contentLength)
        .inputStream(s3Object.getObjectContent())
        .build();
  }

  private ObjectMetadata extractObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();

    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());

    objectMetadata
        .getUserMetadata()
        .put("extension", FilenameUtils.getExtension(file.getOriginalFilename()));

    return objectMetadata;
  }

  private void initializeBucket() {
    if (!amazonS3.doesBucketExistV2(bucketName)) {
      amazonS3.createBucket(bucketName);
    }
  }
}
