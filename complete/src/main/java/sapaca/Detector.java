package sapaca;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect;

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
    private String originalImagePath;
    private String saveImagePath;
    private IplImage originalImage;
    private IplImage grayImage;
    private ArrayList<IplImage> croppedFaces = new ArrayList<>();
    private ArrayList<Face> faceObjects = new ArrayList<>();
    private IplImage croppedImage;
    private PartToDetect part;
    private int detectedFaces;

    private static String xmlFile;
    private int counter;


    //Face

    public Detector(String originalImagePath, String saveImagePath) {
        this.part = PartToDetect.FACE;
        this.setOriginalImagePath(originalImagePath);
        this.setSaveImagePath(saveImagePath);
        System.console().writer().println(getOriginalImagePath());

        setXmlFile("haarcascade_frontalface_alt.xml");

        originalImage = cvLoadImage(getOriginalImagePath(), 1);
        processImage(originalImage);
        detectFaces();
    }

    public Detector(PartToDetect part, IplImage image) {
        if (part.equals(PartToDetect.EYES)) {
            this.part = PartToDetect.EYES;
            setXmlFile("frontalEyes.xml");

            processImage(image);
        }

        if (part.equals(PartToDetect.PEDESTRIAN)) {

        }
    }

    private void setXmlFile(String xmlPart) {
        xmlFile = getClass().getClassLoader().getResource(".").getFile() + xmlPart;
        if (System.getProperty("os.name").contains("indow")) {
            xmlFile = xmlFile.substring(1, xmlFile.length());
        }
    }

    private void processImage(IplImage image) {
        originalImage = image;
        grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
    }

    private void detectFaces() {
        CvMemStorage storage = CvMemStorage.create();
        CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(xmlFile));
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
        cvSaveImage(getSaveImagePath(), originalImage);
        System.console().writer().println(detectedFaces);
    }

    private void detectEyes() {

    }

    private IplImage cropImage(IplImage image) {
        croppedImage = new IplImage();
        croppedImage = IplImage.create(cvGetSize(originalImage), originalImage.depth(), originalImage.nChannels());
        cvCopy(originalImage, croppedImage);

        cvResetImageROI(originalImage);

        if (part.equals(PartToDetect.FACE)) {
            Face faceObject = new Face(croppedImage);
            faceObjects.add(counter, faceObject);
        }

        if(part.equals((PartToDetect.EYES))) {

        }

        return croppedImage;
    }



    public String getOriginalImagePath() {
        return originalImagePath;
    }

    public void setOriginalImagePath(String originalImagePath) {
        this.originalImagePath = originalImagePath;
    }

    public String getSaveImagePath() {
        return saveImagePath;
    }

    public void setSaveImagePath(String saveImagePath) {
        this.saveImagePath = saveImagePath;
    }

    protected ArrayList<Face> getFaces() {
        return faceObjects;
    }


}
