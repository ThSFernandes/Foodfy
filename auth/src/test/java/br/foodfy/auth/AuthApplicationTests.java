package br.foodfy.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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

import br.foodfy.auth.controllers.AuthenticationController;
import br.foodfy.auth.controllers.ProductController;
import br.foodfy.auth.domain.product.Product;
import br.foodfy.auth.domain.user.User;
import br.foodfy.auth.domain.user.UserRole;
import br.foodfy.auth.infra.security.TokenService;
import br.foodfy.auth.repositories.ProductRepository;
import br.foodfy.auth.repositories.UserRepository;
import br.foodfy.auth.services.AuthorizationService;

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
		//RegisterDTO registerDTO = new RegisterDTO("testuser", "testpass", UserRole.USER);

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
		//ProductRequestDTO productDTO = new ProductRequestDTO("Test Product", 100);

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
