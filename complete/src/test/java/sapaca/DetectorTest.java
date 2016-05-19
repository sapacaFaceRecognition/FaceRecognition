package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Created by caro on 12.05.2016.
 */
public class DetectorTest {
    private IplImage image;
    private PartFactory partFactory;
    private Part partFace;
    private Detector detection;

    @Before
    public void setUp() throws Exception {
        partFactory = new PartFactory();
        partFace = partFactory.load(PartToDetect.FACE);
        image = cvCreateImage(cvSize(10, 10), IPL_DEPTH_1U,3);
        //detection = new Detector(partFace, image);
    }
    @Test
    public void getFalseTest() throws Exception {
        //assertThat(detection.getFaces().size(), is(0));
    }

    @Test
    public void getXmlPathFaceTest() throws Exception {
        //assertTrue(detection.getXmlPath().contains("haarcascade_frontalface_alt.xml"));
    }

    @Test
    public void getFaceAgeObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getAge(), is(0));
        face.setAge(20);
        assertThat(face.getAge(), is(20));
    }

    @Test
    public void faceBooleanObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.isFace(), is(false));
    }

    @Test
    public void faceLocationObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getLocation(), is(nullValue()));
        face.setLocation("GER");
        assertThat(face.getLocation(), is("GER"));
    }

    @Test
    public void faceFirstNameObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getFirstName(), is(nullValue()));
        face.setFirstName("John");
        assertThat(face.getFirstName(), is("John"));
    }

    @Test
    public void faceLastNameObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getLastName(), is(nullValue()));
        face.setLastName("Doe");
        assertThat(face.getLastName(), is("Doe"));
    }

    @Test
    public void faceIDObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getId(), is(0L));
        face.setId(1234L);
        assertThat(face.getId(), is(1234L));
    }

    @Test
    public void faceNationalityObjectTest() throws Exception {
        Face face = new Face();
        assertThat(face.getNationality(), is(nullValue()));
        face.setNationality("GER");
        assertThat(face.getNationality(), is("GER"));
    }

    @Test
    public void croppedImageTest() throws Exception {
        Face face = new Face();
        assertThat(face.getCroppedFace(), is(nullValue()));
    }
}