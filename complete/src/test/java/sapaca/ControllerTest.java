package sapaca;


import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@TestPropertySource("/test.properties")
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
			mockMvc.perform(MockMvcRequestBuilders.get("/home.html")).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("home"));
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
	public void testFaceDetection() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/face_detection.html"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("face_detection"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testBrowseImages() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/browse_images.html"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("browse_images"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testAboutUs() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/about_us.html"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.forwardedUrl("about_us"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testGetCurrentImage() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/get_current_image.html").param("id", "1"))
					.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
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
