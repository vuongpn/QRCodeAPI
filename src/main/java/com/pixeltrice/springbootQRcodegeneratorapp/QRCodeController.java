package com.pixeltrice.springbootQRcodegeneratorapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
public class QRCodeController {
	private static final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/"+timeStamp+".png";

	
    @GetMapping(value = "/qrcode/{codeText}/{width}/{height}")
		public void download(
				@PathVariable("codeText") String codeText,
				@PathVariable("width") Integer width,
				@PathVariable("height") Integer height)
			    throws Exception {
			        QRCodeGenerator.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH);
			    }

    @GetMapping(value = "/generateQRCode/{codeText}")
   	public ResponseEntity<ResponseEntity<byte[]>> generateQRCode(
   			@PathVariable("codeText") String codeText)
   		    throws Exception {
			ResponseEntity<byte[]> responseEntity = QRCodeGenerator.QRCodeImage(codeText);
			//File fileHtml = new File("D:\\spring-boot-QR-code-generator-app\\src\\main\\resources\\index.html)");
		    //BufferedWriter BW = new BufferedWriter(new FileWriter(fileHtml));
			//BW.write();
		    //String s = new String(bytes, StandardCharsets.UTF_8);
		return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.QRCodeImage(codeText));
   		    }
   	


}
