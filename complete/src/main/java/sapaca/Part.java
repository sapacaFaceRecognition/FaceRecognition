package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifier;

/**
 * Created by caro on 05.05.2016.
 */
public interface Part {
    String setXmlFile();
    CvHaarClassifierCascade loadClassifier();
    IplImage getGrayImage();
    String getXmlPath();
}
