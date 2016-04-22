package sapaca;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new MainController()).build();
	}

	@Test
	public void testLogin() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/login.html")).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testHome() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/home.html")).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testAuthentication() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/home.html").param("inputUsername", "admin")
					.param("inputPassword", "test")).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("login"));
			mockMvc.perform(MockMvcRequestBuilders.post("/home.html").param("inputUsername", "admin")
					.param("inputPassword", "sapaca")).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("home"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testNotFound() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/bla")).andExpect(MockMvcResultMatchers.status().isNotFound());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
