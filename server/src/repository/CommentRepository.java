package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Comment;
import repository.interfaces.iCommentRepository;
import repository.interfaces.iIdGenerator;
import repository.interfaces.iStream;

public class CommentRepository  extends UniversalRepository<Comment, Long> implements iCommentRepository<Comment, Long> {

	public CommentRepository(iStream<Comment> stream, iIdGenerator<Long> idGenerator) {
		super(stream, idGenerator);
	}

	@Override
	public List<Comment> findCommentsByApartmentId(Long id) {
		List<Comment> comments = new ArrayList<Comment>(findAll());
		return comments.stream().filter(c -> c.getApartment().getId() == id)
				.collect(Collectors.toList());
	}
	
	

}
