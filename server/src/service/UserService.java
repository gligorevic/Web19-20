package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import at.favre.lib.crypto.bcrypt.BCrypt;
import config.SecurityConstants;
import domain.User;
import dto.ChangePasswordDTO;
import dto.LoginDTO;
import dto.UserDTO;
import exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import repository.DBRepository;

public class UserService {
	@Inject
	private DBRepository db;

	public List<UserDTO> findAll() {
		List<UserDTO> users = new ArrayList<UserDTO>();
		db.getUserRepository().findAll().stream().forEach(user -> users.add(new UserDTO(user)));
		;
		return users;
	}

	public String register(UserDTO newUser) throws CustomException {
		if (db.getUserRepository().findByUsername(newUser.getUsername()) != null)
			throw new CustomException(Status.BAD_REQUEST);
		newUser.setPassword(new String(BCrypt.withDefaults().hashToChar(12, newUser.getPassword().toCharArray())));
		User user = db.getUserRepository().save(new User(newUser));

		return generateJWTForUser(user);
	}

	public User registerHost(UserDTO newHost) throws CustomException {
		if (db.getUserRepository().findByUsername(newHost.getUsername()) != null)
			throw new CustomException(Status.BAD_REQUEST);
		newHost.setPassword(new String(BCrypt.withDefaults().hashToChar(12, newHost.getPassword().toCharArray())));
		return db.getUserRepository().save(new User(newHost));
	}

	private String generateJWTForUser(User user) {
		Date now = new Date(System.currentTimeMillis());

		Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRE);

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("role", user.getRole());

		String subject = user.getId().toString();
		return Jwts.builder().setSubject(subject).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SecurityConstants.SECRET)).compact();

	}

	public String login(LoginDTO authData) throws CustomException {
		User user = db.getUserRepository().findByUsername(authData.getUsername());
		BCrypt.Result result = BCrypt.verifyer().verify(authData.getPassword().toCharArray(), user.getPassword());
		if (!result.verified)
			throw new CustomException("Bad credentials.", Status.BAD_REQUEST);

		return generateJWTForUser(user);
	}

	public UserDTO getUser(Long id, String token) throws CustomException {
		User user = db.getUserRepository().findById(id);
		if (user == null)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		if (getIdFromJWT(token) != id)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		return new UserDTO(user);
	}

	private Long getIdFromJWT(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token.substring(7))
					.getBody();
			return Long.parseLong(claims.get("id", String.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public UserDTO editUser(UserDTO userData, Long id, String token) throws CustomException {
		User user = db.getUserRepository().findById(id);
		if (user == null)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		if (getIdFromJWT(token) != id)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);

		if (isNotEmpty(userData.getName()))
			user.setName(userData.getName());
		if (isNotEmpty(userData.getLastName()))
			user.setLastName(userData.getLastName());
		if (!userData.getGender().equals(user.getGender()))
			user.setGender(userData.getGender());

		db.getUserRepository().update(user);

		return userData;
	}

	public ChangePasswordDTO changePassword(ChangePasswordDTO newPasswordData, Long id, String token)
			throws CustomException {
		User user = db.getUserRepository().findById(id);
		if (user == null)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);
		if (getIdFromJWT(token) != id)
			throw new CustomException("Unauthorized", Status.UNAUTHORIZED);

		BCrypt.Result result = BCrypt.verifyer().verify(newPasswordData.getOldPassword().toCharArray(),
				user.getPassword());
		if (!result.verified)
			throw new CustomException("Old password is not correct.", Status.BAD_REQUEST);

		user.setPassword(
				new String(BCrypt.withDefaults().hashToChar(12, newPasswordData.getNewPassword().toCharArray())));
		db.getUserRepository().update(user);

		return newPasswordData;
	}

	private boolean isNotEmpty(String name) {
		return name != null && name.length() > 0;
	}

	public User getUserByToken(String token) {
		Long id = getIdFromJWT(token);
		return db.getUserRepository().findById(id);
	}

}
