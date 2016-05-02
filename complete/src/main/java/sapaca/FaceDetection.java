package sapaca;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRect;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvResetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
public class FaceDetection {

	private int detectedFaces;
	private String originalImagePath;
	private String saveImagePath;
	private IplImage originalImage;
	private IplImage grayImage;
	private IplImage croppedFace;
	private ArrayList<Face> faceObjects = new ArrayList<>();
	private ArrayList<IplImage> croppedFaces = new ArrayList<>();
	private int counter;

	private static String xmlFile;

	public FaceDetection(String originalImagePath, String saveImagePath) {
		this.setOriginalImagePath(originalImagePath);
		this.setSaveImagePath(saveImagePath);
		xmlFile = getClass().getClassLoader().getResource(".").getFile() + "haarcascade_frontalface_alt.xml";
		if (System.getProperty("os.name").contains("indow")) {
			xmlFile = xmlFile.substring(1, xmlFile.length());
		}
		detect();
	}

	private void detect() {

		System.console().writer().println(getOriginalImagePath());

		originalImage = cvLoadImage(getOriginalImagePath(), 1);
		grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
		cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);

		CvMemStorage storage = CvMemStorage.create();

		// Instantiate classifier cascade
		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(xmlFile));

		CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, 2, 0);

		// Draw rectangles (on original image)
		for (int i = 0; i < faces.total(); i++) {
			counter = i;
			CvRect r = new CvRect(cvGetSeqElem(faces, i));
			cvRectangle(originalImage, cvPoint(r.x(), r.y()), cvPoint(r.x() + r.width(), r.y() + r.height()),
					CvScalar.GREEN, 1, CV_AA, 0);

			CvRect r2 = cvRect(r.x() - 100, r.y() - 100, r.width() + 200, r.height() + 200);
			cvSetImageROI(originalImage, r2);
			croppedFaces.add(i, cropFace());
		}

		detectedFaces = faces.total();
		cvSaveImage(getSaveImagePath(), originalImage);
		System.console().writer().println(detectedFaces);

	}

	private IplImage cropFace() {
		croppedFace = new IplImage();
		croppedFace = IplImage.create(cvGetSize(originalImage), originalImage.depth(), originalImage.nChannels());
		cvCopy(originalImage, croppedFace);

		cvResetImageROI(originalImage);

		Face faceObject = new Face(croppedFace);
		faceObjects.add(counter, faceObject);

		return croppedFace;
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