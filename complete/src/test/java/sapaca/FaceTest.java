package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FaceTest {
	private Face face;
	
	@Before
	public void setUp() {
		face = new Face();
	}
	
	@Test
	public void testConstructor() {
		face = new Face(new IplImage());
		face = new Face();
	}
	
	@Test
	public void testGetterSetter() {
		face.setAge(42);
		assertEquals(42, face.getAge());
		face.setFirstName("Harald");
		assertEquals("Harald", face.getFirstName());
		face.setLastName("Heinrich");
		assertEquals("Heinrich", face.getLastName());
		face.setGender(Gender.MALE);
		assertEquals(Gender.MALE, face.getGender());
		face.setId(42);
		assertEquals(42, face.getId());
		face.setLocation("Deutschland");
		assertEquals("Deutschland", face.getLocation());
		face.setNationality("Deutsch");
		assertEquals("Deutsch", face.getNationality());
		
	}
	
	

}
