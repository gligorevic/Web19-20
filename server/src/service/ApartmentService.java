package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import domain.Apartment;
import domain.User;
import exception.CustomException;
import repository.DBRepository;

public class ApartmentService {

	@Inject
	private UserService userService;
	
	@Inject
	private DBRepository db;
	
	public List<Apartment> getUserApartments(Long id, String token) throws CustomException {
		userService.getUser(id, token);
		return db.getApartmentRepository().findApartmentsByUserId(id);
	}

	public Apartment createApartment(Apartment apartment, List<FormDataBodyPart> files, String token) throws IOException {
		User user = userService.getUserByToken(token);
		apartment.setHost(user);
		
		if(files != null) {
			List<String> imagesPaths = new ArrayList<>();
			String folder = "images/" + user.getId();
			Files.createDirectories(Paths.get(folder));
		
			for (int i = 0; i < files.size(); i++) {
				BodyPartEntity bodyPartEntity = (BodyPartEntity) files.get(i).getEntity();
				String fileName = files.get(i).getContentDisposition().getFileName();
				imagesPaths.add(folder + "/" + fileName);
				saveFile(bodyPartEntity.getInputStream(), folder, fileName);	
			}
			apartment.setImages(imagesPaths); 
		}
		System.out.println(apartment.getCheckInTime());
		db.getApartmentRepository().save(apartment);
		apartment.setHost(null);
		return apartment;
	}
	
	private void saveFile(InputStream file, String folder, String fileName) throws IOException {
		Path path = Paths.get(folder + "/" + fileName);
		System.out.println(path.toString());
		File fileToSaveIn = new File(path.toString());
        copyInputStreamToFile(file, fileToSaveIn);
	}
	
		private void copyInputStreamToFile(InputStream inputStream, File file)
			throws IOException {

	        try (FileOutputStream outputStream = new FileOutputStream(file)) {

	            int read;
	            byte[] bytes = new byte[1024];

	            while ((read = inputStream.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            }

	        }

	    }

}
