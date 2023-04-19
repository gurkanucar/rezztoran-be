package com.rezztoran.rezztoranbe.service;

import com.google.zxing.WriterException;
import java.io.IOException;

/** The interface Qr code service. */
public interface QRCodeService {

  /**
   * Generate qr code with logo byte [ ].
   *
   * @param text the text
   * @param width the width
   * @param height the height
   * @param isColored the is colored
   * @return the byte [ ]
   * @throws WriterException the writer exception
   * @throws IOException the io exception
   */
  byte[] generateQRCodeWithLogo(String text, int width, int height, boolean isColored)
      throws WriterException, IOException;
}
