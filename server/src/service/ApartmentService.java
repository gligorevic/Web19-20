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

import domain.Amenity;
import domain.Apartment;
import domain.Role;
import domain.User;
import dto.ApartmentCardDTO;
import dto.SearchApartmentDTO;
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
		List<Amenity> amenityList = new ArrayList<>();
		for(Amenity a : apartment.getAmenities()) {
			Amenity amenityToBeAdded = db.getAmenityRepository().findById(a.getId());
			if(!amenityToBeAdded.getDeleted()) amenityList.add(amenityToBeAdded);
		}
		apartment.setAmenities(amenityList);
		User host = apartment.getHost();
		host.setPassword(null);
		apartment.setHost(host);
		return apartment;
	}

	public String changeApartmentStatus(Long id, String status, String token) throws CustomException {
		User host = userService.getUserByToken(token);
		Apartment apartment = db.getApartmentRepository().findById(id);
		if(apartment.getHost().getId() != host.getId() && host.getRole() != Role.ADMIN) throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		if(domain.Status.ACTIVE.name().equals(status)) apartment.setStatus(domain.Status.ACTIVE);
		else apartment.setStatus(domain.Status.INACTIVE);
		
		db.getApartmentRepository().update(apartment);
		return status;
	}

	public List<ApartmentCardDTO> getAllApartments() {
		return db.getApartmentRepository().findAll().stream().filter(a -> !a.getDeleted() && a.getStatus() == domain.Status.ACTIVE).map(a -> new ApartmentCardDTO(a)).collect(Collectors.toList());
	}

	public List<ApartmentCardDTO> getAllApartmentsByAdmin() {
		return db.getApartmentRepository().findAll().stream().filter(a -> !a.getDeleted()).map(a -> new ApartmentCardDTO(a)).collect(Collectors.toList());	
	}

	public Apartment deleteApartment(Long id, String status, String token) throws CustomException {
		User host = userService.getUserByToken(token);
		Apartment apartment = db.getApartmentRepository().findById(id);
		if(apartment.getHost().getId() != host.getId() && host.getRole() != Role.ADMIN) throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		apartment.setDeleted(true);
		db.getApartmentRepository().update(apartment);
		apartment.setHost(null);
		return apartment;
	}

	public Apartment editApartment(Long id, Apartment apartment, List<FormDataBodyPart> files, String token) throws IOException, CustomException {
		Apartment apartmentToBeChanged = db.getApartmentRepository().findById(id);
		User host = userService.getUserByToken(token);		
		if(apartmentToBeChanged.getHost().getId() != host.getId() && host.getRole() != Role.ADMIN) throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		
		if (files != null) {
			List<String> imagesPaths = new ArrayList<>();
			String folder = "images/" + apartment.getHost().getId();
			Files.createDirectories(Paths.get(folder));

			for (int i = 0; i < files.size(); i++) {
				BodyPartEntity bodyPartEntity = (BodyPartEntity) files.get(i).getEntity();
				String fileName = files.get(i).getContentDisposition().getFileName();
				imagesPaths.add(folder + "/" + fileName);
				saveFile(bodyPartEntity.getInputStream(), folder, fileName);
			}
			apartmentToBeChanged.setImages(imagesPaths);
		}
		apartmentToBeChanged.changeObject(apartment);

		db.getApartmentRepository().update(apartmentToBeChanged);
		apartment.setHost(null);
		return apartment;
	}

	public List<ApartmentCardDTO> searchApartments(SearchApartmentDTO searchData) {
		List<Apartment> apartments = db.getApartmentRepository().findAll();
		
		List<ApartmentCardDTO> apartmentDTOs = apartments.stream().filter(a -> !a.getDeleted() && a.getStatus() == domain.Status.ACTIVE && a.getPricePerNight() >= searchData.getPriceFrom() && (a.getPricePerNight() <= searchData.getPriceTo() || searchData.getPriceTo() == 100 )
				&& a.getGuestNumber() == searchData.getGuestNumber() 
				&& a.getRoomNumber() >= searchData.getMinRoomNumber() && a.getRoomNumber() <= searchData.getMaxRoomNumber()
				&& (a.getLocation().getAddress().getCountry().toLowerCase().contains(searchData.getCountry().toLowerCase()) || searchData.getCountry().toLowerCase().contains(a.getLocation().getAddress().getCountry().toLowerCase()))
				&& ((a.getLocation().getAddress().getCity().toLowerCase().contains(searchData.getCity().toLowerCase()) || searchData.getCity().toLowerCase().contains(a.getLocation().getAddress().getCity().toLowerCase()))))
				.map(a -> new ApartmentCardDTO(a)).collect(Collectors.toList());
		
		return apartmentDTOs;
	}

}
