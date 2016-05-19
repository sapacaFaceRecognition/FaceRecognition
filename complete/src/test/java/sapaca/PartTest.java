package sapaca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
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
    public void testSetXmlNameFace() throws Exception{
        Part partFace = partFactory.load(PartToDetect.FACE);
        assertEquals(partFace.getXmlName(), "haarcascade_frontalface_alt.xml");
    }

    @Test
    public void testSetXmlNameEyes() throws Exception{
        Part partEyes = partFactory.load(PartToDetect.EYES);
        assertEquals(partEyes.getXmlName(), "frontalEyes.xml");
    }

    @Test
    public void testSetXmlNamePerson() throws Exception{
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertEquals(partPerson.getXmlName(), "person.xml");
    }

    @Test
    public void getPartToDetectFaceTest() throws Exception{
        Part partPerson = partFactory.load(PartToDetect.FACE);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.FACE));
    }

    @Test
    public void getPartToDetectEyesTest() throws Exception{
        Part partPerson = partFactory.load(PartToDetect.EYES);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.EYES));
    }

    @Test
    public void getPartToDetectPersonTest() throws Exception{
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertThat(partPerson.getPartToDetect(), is(PartToDetect.PERSON));
    }

    @Test
    public void getXmlPathFaceTest() throws Exception{
        Part partFace = partFactory.load(PartToDetect.FACE);
        assertThat(partFace.getXmlPath(), is(""));
    }

    @Test
    public void getXmlPathEyesTest() throws Exception{
        Part partEyes = partFactory.load(PartToDetect.EYES);
        assertThat(partEyes.getXmlPath(), is(""));
    }

    @Test
    public void getXmlPathPersonTest() throws Exception{
        Part partPerson = partFactory.load(PartToDetect.PERSON);
        assertThat(partPerson.getXmlPath(), is(""));
    }

    @Test
    public void EnumFaceTest() throws Exception {
        assertThat(PartToDetect.valueOf("FACE"), is(notNullValue()));
    }

    @Test
    public void EnumEyesTest() throws Exception {
        assertThat(PartToDetect.valueOf("EYES"), is(notNullValue()));
    }

    @Test
    public void EnumPersonTest() throws Exception {
        assertThat(PartToDetect.valueOf("PERSON"), is(notNullValue()));
    }
}
