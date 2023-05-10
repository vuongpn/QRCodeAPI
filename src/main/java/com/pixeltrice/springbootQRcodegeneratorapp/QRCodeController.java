package com.pixeltrice.springbootQRcodegeneratorapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

@RestController
public class QRCodeController {
	private static final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/"+timeStamp+".png";

	private final int LENGTH_CONTENT_FILE_HTML = 82;

	
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
			@PathVariable("codeText") String codeText){
		try (FileInputStream inputStream
					 = new FileInputStream("D:\\spring-boot-QR-code-generator-app\\src\\main\\resources\\static\\index.html"

		)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuilder fileContents = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				fileContents.append(line);
			}
			if(fileContents.length() != LENGTH_CONTENT_FILE_HTML ) {
				int base64Index = fileContents.indexOf("base64,");
				String myStringToAppend = Base64.getEncoder().encodeToString(QRCodeGenerator.QRCodeImage(codeText).getBody());
				fileContents.delete(fileContents.indexOf("base64,")+7,fileContents.indexOf(" alt=")-1);
				fileContents.insert(base64Index+7, myStringToAppend);
				outputFile(fileContents);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.QRCodeImage(codeText));

	}
	@RequestMapping("/")
	public ModelAndView index () {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.html");
		return modelAndView;
	}

	private void outputFile(StringBuilder fileContents){
		try (FileOutputStream outputStream = new FileOutputStream("D:\\spring-boot-QR-code-generator-app\\src\\main\\resources\\static\\index.html")) {
			outputStream.write(fileContents.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   	


}
