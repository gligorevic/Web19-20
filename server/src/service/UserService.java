package service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import at.favre.lib.crypto.bcrypt.BCrypt;
import config.SecurityConstants;
import domain.User;
import dto.LoginDTO;
import dto.UserDTO;
import exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import repository.LongIdGenerator;
import repository.UserRepository;
import repository.streams.JSONFileStream;


public class UserService {
	private UserRepository userRepository;

	public UserService() {
		JSONFileStream<User> jsonFileStream = new JSONFileStream<User>("users.txt", User.class);
		LongIdGenerator lidgen = new LongIdGenerator();
		lidgen.initializeId(Long.valueOf(jsonFileStream.readAll().size()));
		this.userRepository = new UserRepository(jsonFileStream, lidgen);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public String register(UserDTO newUser) throws CustomException {
		if(userRepository.findByUsername(newUser.getUsername()) != null) throw new CustomException(Status.BAD_REQUEST);
		newUser.setPassword(new String(BCrypt.withDefaults().hashToChar(12, newUser.getPassword().toCharArray())));
		User user = userRepository.save(new User(newUser));
		
		return generateJWTForUser(user);	
	}

	private String generateJWTForUser(User user) {
		Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRE);
		
		Map<String,Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());

        
        String subject = user.getId().toString();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SecurityConstants.SECRET))
                .compact();
	
	}

	public String login(LoginDTO authData) throws CustomException {
		User user = userRepository.findByUsername(authData.getUsername());
		BCrypt.Result result = BCrypt.verifyer().verify(authData.getPassword().toCharArray(), user.getPassword());
		if(!result.verified) throw new CustomException("Bad credentials.",Status.BAD_REQUEST);
		
		return generateJWTForUser(user);
	}
	
}
