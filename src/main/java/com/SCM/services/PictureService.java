package com.SCM.services;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

	String uploadPicture(MultipartFile picture, String filename);

	String getUrlFromPublicId(String publicId);
}
