package programming.techie.springredditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.dto.AuthenticationResponse;
import programming.techie.springredditclone.dto.LoginRequest;
import programming.techie.springredditclone.dto.RefreshTokenRequest;
import programming.techie.springredditclone.dto.RegisterRequest;
import programming.techie.springredditclone.exceptions.SpringRedditException;
import programming.techie.springredditclone.model.NotificationEmail;
import programming.techie.springredditclone.model.User;
import programming.techie.springredditclone.model.VerificationToken;
import programming.techie.springredditclone.repository.UserRepository;
import programming.techie.springredditclone.repository.VerificationTokenRepository;
import programming.techie.springredditclone.security.JwtProvider;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final AuthenticationManager authenticationManager ;
	private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

private final MailService MailService ;


	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);

		String token = generateVerificationToken(user);
		MailService.sendMail(new NotificationEmail("Please Activate your Account",
				user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		verificationTokenRepository.save(verificationToken);
		
		return token ;
		
		
	}
	@Transactional(readOnly =true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken =verificationTokenRepository
				.findBytoken(token);
		verificationToken.orElseThrow(()->new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());
	}
@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
	User user =	userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("User Not Found "));
		user.setEnabled(true);
		userRepository.save(user);
		
	}

public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String token = jwtProvider.generateToken(authenticate);
    return AuthenticationResponse.builder()
            .authenticationToken(token)
            .refreshToken(refreshTokenService.generateRefreshToken().getToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
            .username(loginRequest.getUsername())
            .build();
}
public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
    String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
    return AuthenticationResponse.builder()
            .authenticationToken(token)
            .refreshToken(refreshTokenRequest.getRefreshToken())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
            .username(refreshTokenRequest.getUsername())
            .build();
}
}
