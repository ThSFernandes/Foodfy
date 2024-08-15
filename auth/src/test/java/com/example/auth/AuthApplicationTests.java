package com.example.auth;

import com.example.auth.controllers.AuthenticationController;
import com.example.auth.controllers.ProductController;
import com.example.auth.domain.product.Product;
import com.example.auth.domain.product.ProductRequestDTO;
import com.example.auth.domain.user.*;
import com.example.auth.infra.security.TokenService;
import com.example.auth.repositories.ProductRepository;
import com.example.auth.repositories.UserRepository;
import com.example.auth.services.AuthorizationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthApplicationTests {



	private MockMvc mockMvc;

	@InjectMocks
	private AuthenticationController authController;

	@InjectMocks
	private ProductController productController;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private TokenService tokenService;

	@Mock
	private AuthorizationService authorizationService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(authController, productController)
				.build();
	}

	@Test
	void testRegister() throws Exception {
		RegisterDTO registerDTO = new RegisterDTO("testuser", "testpass", UserRole.USER);

		when(userRepository.findByLogin(anyString())).thenReturn(null);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedTestPass");
		when(userRepository.save(any(User.class))).thenReturn(new User("testuser", "encodedTestPass", UserRole.USER));

		mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"login\":\"testuser\",\"password\":\"testpass\",\"role\":\"USER\"}"))
				.andExpect(status().isOk());
	}



	@Test
	void testPostProduct() throws Exception {
		ProductRequestDTO productDTO = new ProductRequestDTO("Test Product", 100);

		mockMvc.perform(MockMvcRequestBuilders.post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"Test Product\",\"price\":100}")
						.header(HttpHeaders.AUTHORIZATION, "Bearer mockToken"))
				.andExpect(status().isOk());

		verify(productRepository, times(1)).save(any(Product.class));
	}

	@Test
	void testGetAllProducts() throws Exception {
		Product product = new Product("1", "Test Product", 100);
		when(productRepository.findAll()).thenReturn(Arrays.asList(product));

		mockMvc.perform(MockMvcRequestBuilders.get("/product")
						.header(HttpHeaders.AUTHORIZATION, "Bearer mockToken"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Test Product"))
				.andExpect(jsonPath("$[0].price").value(100));
	}
}
