package programming.techie.springredditclone.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import programming.techie.springredditclone.dto.AuthenticationResponse;
import programming.techie.springredditclone.dto.LoginRequest;
import programming.techie.springredditclone.dto.RefreshTokenRequest;
import programming.techie.springredditclone.dto.RegisterRequest;
import programming.techie.springredditclone.service.AuthService;
import programming.techie.springredditclone.service.RefreshTokenService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest ) {
		authService.signup(registerRequest);
		
		return new ResponseEntity<>("user Registration succesful",HttpStatus.OK);
		
	}
	 @GetMapping("accountVerification/{token}")
	    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
	       authService.verifyAccount(token);
	      return new ResponseEntity<>("Account Activated Successfully",HttpStatus.OK);
	    }

	 @PostMapping("/login")
	 public AuthenticationResponse login (@RequestBody LoginRequest loginRequest) {
		return  authService.login(loginRequest);
		 
		 
	 }
	 @PostMapping("/refresh/token")
	    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	        return authService.refreshToken(refreshTokenRequest);
	    }

	    @PostMapping("/logout")
	    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
	        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
	        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	    }
	
}
