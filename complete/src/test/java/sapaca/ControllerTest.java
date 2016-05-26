//package sapaca;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
//import org.springframework.test.context.jdbc.SqlGroup;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@IntegrationTest("server.port:0")
//@SpringApplicationConfiguration(classes = { Application.class })
//@TestPropertySource("classpath:test.properties")
//@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, value = "classpath:mysql_setup.sql"),
//		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:mysql_shutdown.sql") })
//public class ControllerTest {
//
//	@Autowired
//	WebApplicationContext webApplicationContext;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@Test
//	public void testLogin() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/login.html")).andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testHome() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/home.html")).andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testAuthentication() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/home.html").param("inputUsername", "admin").param("inputPassword",
//				"test")).andExpect(MockMvcResultMatchers.status().isOk());
//		mockMvc.perform(MockMvcRequestBuilders.post("/home.html").param("inputUsername", "admin").param("inputPassword",
//				"sapaca")).andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testFaceDetection() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/face_detection.html"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testFaceRecognition() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/face_recognition.html"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testBrowseImages() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/browse_images.html"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testAboutUs() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/about_us.html")).andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testGetCurrentImage() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/get_current_image").param("id", "1"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//		mockMvc.perform(MockMvcRequestBuilders.get("/get_current_image"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//		mockMvc.perform(MockMvcRequestBuilders.get("/test"));
//		mockMvc.perform(MockMvcRequestBuilders.get("/get_current_image"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testStatistics() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/statistics.html"))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//	}
//
//	@Test
//	public void testNextFace() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/next_face.html"))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//		mockMvc.perform(MockMvcRequestBuilders.get("/test"));
//		mockMvc.perform(MockMvcRequestBuilders.post("/next_face.html").param("firstName", "").param("lastName", "")
//				.param("age", "42").param("nationality", "").param("location", "").param("faceDetected", "true"))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//		mockMvc.perform(MockMvcRequestBuilders.post("/next_face.html").param("noFaceDetected", "true"))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//	}
//
//	@Test
//	public void testDeleteImage() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/delete_image.html").param("id", "1"))
//				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//	}
//
//	@Test
//	public void testNotFound() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/bla")).andExpect(MockMvcResultMatchers.status().isNotFound());
//	}
//
//}
