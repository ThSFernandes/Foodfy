package br.foodfy.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.foodfy.auth.controllers.AuthenticationController;
import br.foodfy.auth.domain.user.*;
import br.foodfy.auth.infra.security.TokenService;
import br.foodfy.auth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class teste {

	@InjectMocks
	private AuthenticationController authController;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenService tokenService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLogin_Failure() {
		// Arrange
		String login = "testuser";
		String password = "testpass";
		AuthenticationDTO authDTO = new AuthenticationDTO(login, password);

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new RuntimeException("Authentication failed"));

		// Act
		ResponseEntity<?> response = authController.login(authDTO);

		// Assert
		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}


	@Test
	void testRegister_Success() {
		// Arrange
		String login = "testuser";
		String password = "testpass";
		String encryptedPassword = "encodedTestPass";
		RegisterDTO registerDTO = new RegisterDTO(login, password, UserRole.USER);

		when(userRepository.findByLogin(login)).thenReturn(null);
		when(passwordEncoder.encode(password)).thenReturn(encryptedPassword);
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		ResponseEntity<?> response = authController.register(registerDTO);

		// Assert
		verify(userRepository).findByLogin(login);
		verify(passwordEncoder).encode(password);  // Verifica se o encode foi chamado
		verify(userRepository).save(any(User.class));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testRegister_UserAlreadyExists() {
		// Arrange
		String login = "testuser";
		String password = "testpass";
		RegisterDTO registerDTO = new RegisterDTO(login, password, UserRole.USER);
		User existingUser = new User(login, "encodedTestPass", UserRole.USER);

		when(userRepository.findByLogin(login)).thenReturn(existingUser);

		// Act
		ResponseEntity<?> response = authController.register(registerDTO);

		// Assert
		verify(userRepository).findByLogin(login);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
