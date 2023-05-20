package com.rezztoran.rezztoranbe.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.rezztoran.rezztoranbe.service.QRCodeService;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/** The type Qr code service. */
@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {
  @Override
  public byte[] generateQRCodeWithLogo(String text, int width, int height, boolean isColored)
      throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

    BufferedImage qrImage;
    BufferedImage logoImage = getLogoImage();

    int logoWidth = isColored ? width / 3 : width / 5;
    int logoHeight = isColored ? height / 3 : height / 5;
    int logoX = (width - logoWidth) / 2;
    int logoY = (height - logoHeight) / 2;

    if (isColored) {
      qrImage =
          MatrixToImageWriter.toBufferedImage(
              bitMatrix, new MatrixToImageConfig(0xFF000000, 0x00FFFFFF));

      // Create a white circular background for the logo
      Ellipse2D.Double backgroundShape =
          new Ellipse2D.Double(logoX - 5, logoY - 5, logoWidth + 10, logoHeight + 10);

      Graphics2D graphics = qrImage.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setClip(backgroundShape);
      graphics.setColor(Color.WHITE);
      graphics.fill(backgroundShape);

      graphics.setClip(null);
    } else {
      qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    Graphics2D graphics = qrImage.createGraphics();
    graphics.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight, null);
    graphics.dispose();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(qrImage, "PNG", outputStream);

    return outputStream.toByteArray();
  }

  private BufferedImage getLogoImage() throws IOException {
    ClassPathResource imageResource = new ClassPathResource("static/rezztoran_logo.png");
    return ImageIO.read(imageResource.getInputStream());
  }
}
