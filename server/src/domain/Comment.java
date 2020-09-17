package domain;

import domain.interfaces.iIdentifiable;
import dto.CommentDTO;

public class Comment implements iIdentifiable<Long> {
	private Long id;
	private User guest;
	private Apartment apartment;
	private String text;
	private int grade;
	private Long reservationId;
	private Boolean showComment = false;
	private Boolean deleted;
	
	public Comment() {
		
	}
	public Comment(CommentDTO dto) {
		this.text = dto.getText();
		this.reservationId = dto.getReservationId();
		this.grade = dto.getGrade();
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Boolean getShowComment() {
		return showComment;
	}

	public void setShowComment(Boolean showComment) {
		this.showComment = showComment;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public User getGuest() {
		return guest;
	}

	public void setGuest(User guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}