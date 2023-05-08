package com.pixeltrice.springbootQRcodegeneratorapp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;

public class QRCodeGenerator {

		public static void generateQRCodeImage(String text, int width, int height, String filePath)
	            throws WriterException, IOException {
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

	        Path path = FileSystems.getDefault().getPath(filePath);
	        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	       
	    }
		
		
		public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
		    QRCodeWriter qrCodeWriter = new QRCodeWriter();
		    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		    
		    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

		    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		    byte[] pngData = pngOutputStream.toByteArray();
		    return pngData;
		}

	public static ResponseEntity<byte[]> QRCodeImage(String text) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000);
			BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(qrCodeImage, "png", baos);
			byte[] imageData = baos.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
