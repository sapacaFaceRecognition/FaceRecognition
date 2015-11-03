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
	public void loginTest() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/login.html")).andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void homeTest() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/home.html")).andExpect(MockMvcResultMatchers.status().isOk());

			mockMvc.perform(
					MockMvcRequestBuilders.post("/home.html").param("username", "admin").param("password", "sapaca"))
					.andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void NotFoundTest() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/bla")).andExpect(MockMvcResultMatchers.status().isNotFound());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
