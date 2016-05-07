package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;

import java.io.File;
import java.net.URL;

import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import static org.bytedeco.javacpp.opencv_core.*;

public class PartPerson implements Part {
    private static String xmlPath;
    private CvHaarClassifierCascade cascade;
    private String xmlName;

    @Override
    public CvHaarClassifierCascade loadClassifier() {
        IplImage greenChannelImage = cvCreateImage(cvSize(100, 100), IPL_DEPTH_8U, 3);
        cvSet(greenChannelImage, cvScalar(0));
        cascade = new CvHaarClassifierCascade(cvLoad(xmlPath));
        for (int x = 20; x <= 40; x++) {
            greenChannelImage.getByteBuffer(x * 100 * 3 + 5 * 3).put(1, (byte) 255);
            greenChannelImage.getByteBuffer(x * 100 * 3 + 20 * 3).put(1, (byte) 255);
        }
        System.out.println(greenChannelImage.toString());
        return cascade;
    }

    @Override
    public String getXmlPath() {
        return xmlPath;
    }

    @Override
    public String getXmlName() {
        xmlName = "person.xml";
        return xmlName;
    }
}
