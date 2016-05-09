package sapaca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by caro on 07.05.2016.
 */
public class PartFactoryTest {
    private PartFactory partFactory;
    @Before
    public void setUp() {
        partFactory = new PartFactory();
    }

    @Test
    public void loadFaceTest() {
        Part face = partFactory.load(PartToDetect.FACE);
        assertEquals(face.getClass(), partFactory.load(PartToDetect.FACE).getClass());
    }

    @Test
    public void loadEyesTest() {
        Part eyes = partFactory.load(PartToDetect.EYES);
        assertEquals(eyes.getClass(), partFactory.load(PartToDetect.EYES).getClass());
    }

    @Test
    public void loadPersonTest() {
        Part person = partFactory.load(PartToDetect.PERSON);
        assertEquals(person.getClass(), partFactory.load(PartToDetect.PERSON).getClass());
    }
}
