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
        detection = new Detector(partFace, image);
    }
    @Test
    public void getFalseTest() throws Exception {
        assertThat(detection.getFaces().size(), is(0));
    }

    @Test
    public void getXmlPathFaceTest() throws Exception {
        //assertTrue(detection.getXmlPath().contains("haarcascade_frontalface_alt.xml"));
    }
}