package sapaca;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;

public class Detector {
    private IplImage grayImage;
    private ArrayList<IplImage> croppedFaces = new ArrayList<>();
    private ArrayList<Face> faceObjects = new ArrayList<>();
    private IplImage croppedImage;
    private PartToDetect part;
    private int detectedFaces;
    private CvHaarClassifierCascade cascade;
    private IplImage processImage;

    private static String xmlFile;
    private int counter;

    public Detector(PartToDetect part, IplImage image) {
        if (part.equals(PartToDetect.FACE)) {
            this.part = PartToDetect.FACE;
            processImage = image;
            setXmlFile();
            loadClassifier();
            detectFaces(processImage(processImage));
        }
        if (part.equals(PartToDetect.EYES)) {
            this.part = PartToDetect.EYES;
            setXmlFile();
            loadClassifier();
            detectEyes(processImage(image));
        }

        if (part.equals(PartToDetect.PEDESTRIAN)) {
        }


    }

    private void setXmlFile() {
        try {
            if (part.equals(PartToDetect.FACE)) {
                URL source = Detector.class.getClassLoader().getResource("haarcascade_frontalface_alt.xml");
                xmlFile = new File(source.toURI()).getAbsolutePath();
            }
            if (part.equals(PartToDetect.EYES)) {
                URL source = Detector.class.getClassLoader().getResource("frontalEyes.xml");
                xmlFile = new File(source.toURI()).getAbsolutePath();
            }
        }
        catch (Exception e) {

        }
    }

    public IplImage processImage(IplImage image) {
        IplImage originalImage = image;
        grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
        return originalImage;
    }

    public CvHaarClassifierCascade loadClassifier() {
        return cascade = new CvHaarClassifierCascade(cvLoad(xmlFile));
    }

    private void detectFaces(IplImage originalImage) {
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
        CvSeq eyes = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, 2, 0);
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

        if (part.equals(PartToDetect.FACE)) {
            Face faceObject = new Face(croppedImage);
            faceObjects.add(counter, faceObject);
        }

        if(part.equals((PartToDetect.EYES))) {
            Face faceObject = new Face(croppedImage);
            faceObjects.add(counter, faceObject);
        }

        return croppedImage;
    }

    protected ArrayList<Face> getFaces() {
        return faceObjects;
    }


}
