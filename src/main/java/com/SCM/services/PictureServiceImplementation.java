package com.SCM.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.configurations.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class PictureServiceImplementation implements PictureService{

	@Autowired
	private CloudinaryConfig cloudinaryConfig;
	
	
	@Override
	public String uploadPicture(MultipartFile picture, String filename) {
		
		try {
			byte[] data = new byte[picture.getInputStream().available()];
			picture.getInputStream().read(data);
			cloudinaryConfig.cloudinary().uploader().upload(data, 
					ObjectUtils.asMap("public_id", filename));
			

			return this.getUrlFromPublicId(filename);
		} 
		catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String getUrlFromPublicId(String publicId) {
		
		return cloudinaryConfig
				.cloudinary()
				.url()
				.transformation(new Transformation<>()
				.width(500)
				.height(500)
				.crop("fill"))
				.generate(publicId);
	}
}
