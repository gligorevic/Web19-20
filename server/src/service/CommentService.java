package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import domain.Apartment;
import domain.Comment;
import domain.Reservation;
import domain.Role;
import domain.User;
import dto.CommentDTO;
import exception.CustomException;
import repository.DBRepository;

public class CommentService {
	@Inject
	private DBRepository db;
	@Inject
	private UserService userService;
		

	public Comment changeCommentStatus(Long id) {
		
		Comment comment = db.getCommentRepository().findById(id);
		comment.setShowComment(!(comment.getShowComment()));
		db.getCommentRepository().update(comment);
		return comment;
	}
	
	public Comment createNew(CommentDTO newComment, String token) throws CustomException {
	
		User user	= userService.getUserByToken(token);
		Reservation reservation = db.getReservationRepository().findById(newComment.getReservationId());
		if(user.getId() != reservation.getGuest().getId()) {
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		};
		
		Comment comment = new Comment(newComment);
		Apartment apartment = db.getApartmentRepository().findById(newComment.getApartmentId());
		comment.setApartment(apartment);
		comment.setGuest(user);
		
		Comment saved = db.getCommentRepository().save(comment);
		reservation.setCommented(true);
		db.getReservationRepository().update(reservation);
		
		return saved ;
	}
	
	public List<Comment> getAllComments(Long apartmentId,String token) throws CustomException{
		User user = userService.getUserByToken(token);
		
		List<Comment> comments = db.getCommentRepository().findCommentsByApartmentId(apartmentId);
		
		if(user.getRole() == Role.GUEST) {
			List<Comment> filtered = comments.stream().filter(c -> c.getShowComment() == true)
							 .collect(Collectors.toList());	
			return filtered;
		}else {	
		return comments;
		}
	}
	
}
