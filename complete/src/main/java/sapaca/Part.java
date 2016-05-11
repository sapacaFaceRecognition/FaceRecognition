package sapaca;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifier;

/**
 * Created by caro on 05.05.2016.
 */
public interface Part {
    CvHaarClassifierCascade loadClassifier();
    String getXmlPath();
    String getXmlName();
    PartToDetect getPartToDetect();
}
