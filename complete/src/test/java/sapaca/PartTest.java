package sapaca;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by caro on 07.05.2016.
 */
public class PartTest {
    private PartFactory partFactory;
    @Before
    public void setUp() {
        partFactory = new PartFactory();
    }
    @Test
    public void testSetXmlNameFace() {
        Part partFace = partFactory.load(PartToDetect.FACE);
        assertEquals(partFace.getXmlName(), "haarcascade_frontalface_alt.xml");
    }

    @Test
    public void testSetXmlNameEyes() {
        Part partEyes = partFactory.load(PartToDetect.EYES);
        assertEquals(partEyes.getXmlName(), "frontalEyes.xml");
    }

    @Test
    public void testSetXmlNamePerson() {
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertEquals(partPerson.getXmlName(), "person.xml");
    }

    @Test
    public void getPartToDetectFaceTest() {
        Part partPerson = partFactory.load(PartToDetect.FACE);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.FACE));
    }

    @Test
    public void getPartToDetectEyesTest() {
        Part partPerson = partFactory.load(PartToDetect.EYES);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.EYES));
    }

    @Test
    public void getPartToDetectPersonTest() {
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.PERSON));
    }

    @Test
    public void getXmlPathFaceTest() {
        Part partFace = partFactory.load(PartToDetect.FACE);
        assertThat(partFace.getXmlPath(), is(""));
    }

    @Test
    public void getXmlPathEyesTest() {
        Part partEyes = partFactory.load(PartToDetect.EYES);
        assertThat(partEyes.getXmlPath(), is(""));
    }

    @Test
    public void getXmlPathPersonTest() {
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertThat(partPerson.getXmlPath(), is(""));
    }

}
