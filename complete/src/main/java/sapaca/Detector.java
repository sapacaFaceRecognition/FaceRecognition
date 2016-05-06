package sapaca;
import org.bytedeco.javacpp.opencv_core.IplImage;
import java.util.ArrayList;
import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

public class Detector {
    private ArrayList<IplImage> croppedFaces = new ArrayList<>();
    private ArrayList<Face> faceObjects = new ArrayList<>();
    private IplImage croppedImage;
    private int detectedFaces;
    private int counter;
    private Part part;
    IplImage grayImage;

    public Detector(Part part, IplImage image) {
        this.part = part;
        detectFaces(image);
    }

    private void detectFaces(IplImage originalImage) {
        CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(part.getXmlPath()));
        grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(originalImage,grayImage,CV_BGR2GRAY);

        CvMemStorage storage = CvMemStorage.create();
        CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, 2, 0);

        for (int i = 0; i < faces.total(); i++) {
            counter = i;
            CvRect r = new CvRect(cvGetSeqElem(faces, i));
            cvRectangle(originalImage, cvPoint(r.x(), r.y()), cvPoint(r.x() + r.width(), r.y() + r.height()),
                    CvScalar.GREEN, 1, CV_AA, 0);

            CvRect r2 = cvRect(r.x() - 100, r.y() - 100, r.width() + 200, r.height() + 200);
            cvSetImageROI(originalImage, r2);
            croppedFaces.add(i, cropImage(originalImage));
        }

        detectedFaces = faces.total();
    }

    private void detectEyes(IplImage originalImage) {
        CvMemStorage storage = CvMemStorage.create();
        CvSeq eyes = cvHaarDetectObjects(part.getGrayImage(), part.loadClassifier(), storage, 1.2, 2, 0);
        for(int i = 0; i < eyes.total(); i++) {
            CvRect r = new CvRect(cvGetSeqElem(eyes, 0));
            cvRectangle(originalImage, cvPoint(r.x(), r.y()), cvPoint(r.x() + r.width(), r.y() + r.height()),
                    CvScalar.GREEN, 1, CV_AA, 0);
            CvRect r2 = cvRect(r.x() - 100, r.y() - 100, r.width() + 200, r.height() + 200);
            cvSetImageROI(originalImage, r2);
            croppedFaces.add(0, cropImage(originalImage));
        }
    }

    private IplImage cropImage(IplImage originalImage) {
        croppedImage = new IplImage();
        croppedImage = IplImage.create(cvGetSize(originalImage), originalImage.depth(), originalImage.nChannels());
        cvCopy(originalImage, croppedImage);

        cvResetImageROI(originalImage);
        Face faceObject = new Face(croppedImage);
        faceObjects.add(counter, faceObject);

        return croppedImage;
    }

    protected ArrayList<Face> getFaces() {
        return faceObjects;
    }
}
