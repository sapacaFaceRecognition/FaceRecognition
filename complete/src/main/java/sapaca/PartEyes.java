package sapaca;


import org.bytedeco.javacpp.opencv_core.IplImage;

import java.io.File;
import java.net.URL;

import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import static org.bytedeco.javacpp.opencv_core.*;


/**
 * Created by caro on 05.05.2016.
 */
public class PartEyes implements Part {
    private static String xmlPath = "";
    private CvHaarClassifierCascade cascade;
    private String xmlName;

    @Override
    public CvHaarClassifierCascade loadClassifier() {
        IplImage greenChannelImage = cvCreateImage(cvSize(210, 210), IPL_DEPTH_8U, 1);
        cascade = new CvHaarClassifierCascade(cvLoad(xmlPath));
        byte color = 0;
        byte borderThick = 10;
        for (int i = 0; i < 10; i++) {
            CvRect cvRect = cvRect(i * borderThick, i * borderThick, 210 - 2 * i * borderThick, 210 - 2 * i * borderThick);
            cvSetImageROI(greenChannelImage, cvRect);
            cvSet(greenChannelImage, cvScalar(color));
            color += 20;
            System.out.println(color + " " + greenChannelImage.toString());
        }
        return cascade;
    }

    @Override
    public String getXmlPath() {
        return xmlPath;
    }

    @Override
    public String getXmlName() {
        xmlName = "frontalEyes.xml";
        return xmlName;
    }

    @Override
    public PartToDetect getPartToDetect() {
        return PartToDetect.EYES;
    }
}
