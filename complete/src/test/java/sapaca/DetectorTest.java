package sapaca;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.junit.Before;
import org.junit.Test;

import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.junit.Assert.*;

import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import org.bytedeco.javacpp.opencv_core.IplImage;

import java.io.File;
import java.net.URL;


/**
 * Created by caro on 04.05.2016.
 */
public class DetectorTest {
    private String imagePathFace;
    private IplImage face;
    private Detector detector;
    private String pathFaceHaarCasc;
    @Before
    public void setUp() throws Exception{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        imagePathFace = classLoader.getResource("TestImages/obama.jpg").getPath();

        if (System.getProperty("os.name").contains("indow")) {
            imagePathFace = imagePathFace.substring(1, imagePathFace.length());
        }
        face = cvLoadImage(imagePathFace);
        detector = new Detector(PartToDetect.FACE, face);
    }
    @Test
    public void setPartToBeDetected() throws Exception {
    }

    @Test
    public void loadFaceClassifierTest() throws Exception {
        assertFalse(detector.loadClassifier().isNull());
    }

    @Test
    public void processImageTest() throws Exception {
    }
}
