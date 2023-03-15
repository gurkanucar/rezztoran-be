package com.rezztoran.rezztoranbe.model;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The type Stored file. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoredFile {

  private String id;
  private String fileName;
  private Long contentLength;
  private InputStream inputStream;
}
