package es.dawequipo3.growing.controllerREST;

import es.dawequipo3.growing.security.jwt.AuthResponse;
import es.dawequipo3.growing.security.jwt.LoginRequest;
import es.dawequipo3.growing.security.jwt.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private UserLoginService userService;

	@Operation(summary = "Login for registered users")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully logged in",
					content = {@Content(
							schema = @Schema(implementation = AuthResponse.class)
					)}
			),
			@ApiResponse(
					responseCode = "401",
					description = "Incorrect email or password",
					content = @Content
			)
	})
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@CookieValue(name = "accessToken", required = false) String accessToken,
			@CookieValue(name = "refreshToken", required = false) String refreshToken,
			@RequestBody LoginRequest loginRequest) {
		
		return userService.login(loginRequest, accessToken, refreshToken);
	}

	@Operation(summary = "Refresh the session token")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Token successfully refreshed",
					content = {@Content(
							schema = @Schema(implementation = AuthResponse.class)
					)}
			)
	})
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(
			@CookieValue(name = "refreshToken", required = false) String refreshToken) {

		return userService.refresh(refreshToken);
	}

	@Operation(summary = "Logout for started sessions")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successfully logged out",
					content = {@Content(
							schema = @Schema(implementation = AuthResponse.class)
					)}
			)
	})
	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logout(HttpServletRequest request, HttpServletResponse response) {

		return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userService.logout(request, response)));
	}
}
