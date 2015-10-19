// javacv0.9
import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
 
public class FaceDetection {
 
    private static final String XML_FILE = "C:\\Users\\caro\\Documents\\GitHub\\FaceDetection\\src\\haarcascade_frontalface_alt.xml";
 
    public static void main(String arg[]) throws Exception {
 
        IplImage originalImage = cvLoadImage("C:\\Users\\caro\\Desktop\\02.jpg",1);

        IplImage grayImage = IplImage.create(originalImage.width(),
                originalImage.height(), IPL_DEPTH_8U, 1);
 
         cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
 
        CvMemStorage storage = CvMemStorage.create();
 
        CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(
                cvLoad(XML_FILE));
 
        CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 1,
                0);

        for (int i = 0; i < faces.total(); i++) {
            CvRect r = new CvRect(cvGetSeqElem(faces, i));
            cvRectangle(originalImage, cvPoint(r.x(), r.y()),
                    cvPoint(r.x() + r.width(), r.y() + r.height()),
                    CvScalar.GREEN, 1, CV_AA, 0);
 
        }
        cvSaveImage("C:\\Users\\caro\\Desktop\\02new.jpg", originalImage);
 
    }
 
}