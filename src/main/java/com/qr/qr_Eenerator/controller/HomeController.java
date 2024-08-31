package com.qr.qr_Eenerator.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qr.qr_Eenerator.model.User;

@Controller
//@RequestMapping("/qr")
public class HomeController {

	@GetMapping("/")
//	@ResponseBody
	public String home() {
		return "home";
	}

	@ModelAttribute("qr")
	public User user() {
		return new User();
	}

	@GetMapping("/qr")
	public String index() {
		return "index"; // This should return the view name "index" (index.html)
	}

	
	@PostMapping("/qr")
	public String generateQR(@ModelAttribute("qr") User user, Model model) throws WriterException, IOException {

		BufferedImage bufferedImage = generateQRImg(user);
		
		File output = new File("D:\\Java\\SpringBoot_Study\\qr_Eenerator\\qr_Eenerator\\src\\main\\resources\\static"+user.getFirstName()+".jpg");
		
		ImageIO.write(bufferedImage, "jpg", output);
		
		model.addAttribute("qr", user);
		
		return "redirect:/?success";
	}

	public static BufferedImage generateQRImg(User user) throws WriterException {

		StringBuilder sb = new StringBuilder();
		sb.append("First Name : ").append(user.getFirstName()).append("| |").append("Last Name : ")
				.append(user.getLastName()).append("| |").append("City : ").append(user.getCity()).append("| |")
				.append("State : ").append(user.getState()).append("| |").append("ZIP Code : ")
				.append(user.getZipCode());
		
		QRCodeWriter codeWriter = new QRCodeWriter();
		
		BitMatrix bitMatrix =codeWriter.encode(sb.toString(), BarcodeFormat.QR_CODE, 200, 200);
		
		return MatrixToImageWriter.toBufferedImage(bitMatrix) ;
	}
}
