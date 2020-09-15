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
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import domain.Apartment;
import domain.User;
import dto.ApartmentCardDTO;
import exception.CustomException;
import repository.DBRepository;

public class ApartmentService {

	@Inject
	private UserService userService;

	@Inject
	private DBRepository db;

	public List<ApartmentCardDTO> getUserApartments(Long id, String token) throws CustomException {
		userService.getUser(id, token);
		return db.getApartmentRepository().findApartmentsByUserId(id).stream().filter(a -> !a.getDeleted())
				.map(a -> new ApartmentCardDTO(a)).collect(Collectors.toList());
	}

	public Apartment createApartment(Apartment apartment, List<FormDataBodyPart> files, String token)
			throws IOException {
		User user = userService.getUserByToken(token);
		apartment.setHost(user);

		if (files != null) {
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

	private void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

		try (FileOutputStream outputStream = new FileOutputStream(file)) {

			int read;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		}

	}

	public Apartment getApartmentById(Long id) {
		Apartment apartment = db.getApartmentRepository().findById(id);
		if (apartment.getDeleted())
			return null;
		User host = apartment.getHost();
		host.setPassword(null);
		apartment.setHost(host);
		return apartment;
	}

	public String changeApartmentStatus(Long id, String status, String token) throws CustomException {
		User host = userService.getUserByToken(token);
		Apartment apartment = db.getApartmentRepository().findById(id);
		if(apartment.getHost().getId() != host.getId()) throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		if(domain.Status.ACTIVE.name().equals(status)) apartment.setStatus(domain.Status.ACTIVE);
		else apartment.setStatus(domain.Status.INACTIVE);
		
		db.getApartmentRepository().update(apartment);
		return status;
	}

	public List<ApartmentCardDTO> getAllApartmentById() {
		return db.getApartmentRepository().findAll().stream().filter(a -> !a.getDeleted() && a.getStatus() == domain.Status.ACTIVE).map(a -> new ApartmentCardDTO(a)).collect(Collectors.toList());
	}

}
