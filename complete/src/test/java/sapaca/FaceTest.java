package sapaca;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by caro on 18.05.2016.
 */
public class FaceTest {
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
