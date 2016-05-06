package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;

import java.io.File;
import java.net.URL;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
public class PartPerson implements Part {
    private static String xmlPath;
    private static IplImage grayImage;
    private CvHaarClassifierCascade cascade;
    @Override
    public String setXmlFile() {
        try {
            URL source = Detector.class.getClassLoader().getResource("haarcascade_frontalface_alt.xml");
            xmlPath = new File(source.toURI()).getAbsolutePath();
        }
        catch (Exception e) {
            System.out.println("Couldn't load xml File");
        }
        return xmlPath;
    }

    @Override
    public CvHaarClassifierCascade loadClassifier() {
        return cascade = new CvHaarClassifierCascade(cvLoad(xmlPath));
    }

    @Override
    public IplImage getGrayImage() {
        return grayImage;
    }

    @Override
    public String getXmlPath() {
        return xmlPath;
    }
}
