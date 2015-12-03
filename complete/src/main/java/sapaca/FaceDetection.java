package sapaca;

// javacv0.9
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

import java.nio.file.Paths;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

/**
 * Class used to detect faces from an image or video source.
 * 
 * @author caro
 * 
 */

/**
 * ToDo: - private void showFacesAndVerify(): isFace Abfrage einbauen irgendwie.
 * damit es richtig auf true/false gesetzt wird (durch Benutzereingabe) - in der
 * selben Methode Strings fuer Name... ueber Eingabe richtig setzen -
 * saveImage(): boolean saveFace setzen ueber Eingabe vom Webinterface -
 * cropFace(): Abfrage fuer x,y,w,h Werte, die ausserhalb des Bildes liegen.
 * z.B. wenn ein Gesicht ganz nah am Rand ist und es keine 100px nach links
 * geht.
 * 
 * @author caro
 *
 */

public class FaceDetection {

	private Face f;
	private int detectedFaces;
	private String originalImagePath;
	private String saveImagePath;
	private IplImage originalImage;
	private IplImage grayImage;
	private IplImage croppedFace;
	private ArrayList<Face> faceObjects = new ArrayList<Face>();
	private ArrayList<IplImage> croppedFaces = new ArrayList<IplImage>();
	private int counter;
	private boolean isFace;
	private boolean saveFace;
	private String firstName;
	private String lastName;
	private String age;
	private String nationality;

<<<<<<< HEAD
	private static String XML_FILE;

	public FaceDetection(String originalImagePath, String saveImagePath) {
		this.setOriginalImagePath(originalImagePath);
		this.setSaveImagePath(saveImagePath);
		XML_FILE = getClass().getClassLoader().getResource(".").getFile() + "haarcascade_frontalface_alt.xml";
		if (System.getProperty("os.name").contains("indow")) {
			XML_FILE = XML_FILE.substring(1, XML_FILE.length());
		}
		detect();
	}

	/**
	 * Detect faces from an image and draw rectangles around the detected faces
	 * at the original
	 * ima@SuppressWarnings("unchecked") @SuppressWarnings("unchecked")
	 */
	private void detect() {

		System.out.println(getOriginalImagePath());

		originalImage = cvLoadImage(getOriginalImagePath(), 1);
		grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
		cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);

		CvMemStorage storage = CvMemStorage.create();

		// Instantiate classifier cascade
		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(XML_FILE));

		CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, 2, 0);

		// Draw rectangles (on original image)
		for (int i = 0; i < faces.total(); i++) {
			counter = i;
			CvRect r = new CvRect(cvGetSeqElem(faces, i));
			cvRectangle(originalImage, cvPoint(r.x(), r.y()), cvPoint(r.x() + r.width(), r.y() + r.height()),
					CvScalar.GREEN, 1, CV_AA, 0);

			CvRect r2 = cvRect(r.x() - 100, r.y() - 100, r.width() + 200, r.height() + 200);
			//cvSetImageROI(originalImage, r2);
//			croppedFaces.add(i, cropFace());
		}

		detectedFaces = faces.total();

		cvSaveImage(getSaveImagePath(), originalImage);
		System.out.println(detectedFaces);

	}

	/**
	 * Method to convert an IplImage to ByteArray
	 * 
	 * @param src
	 *            Image to be converted
	 * @return converted Image
	 */
	// private static byte[] IplImageToByteArray(IplImage src) {
	// Bitmap bm = null;
	// int width = src.width();
	// int height = src.height();
	//
	// IplImage frame2 = IplImage.create(width, height, IPL_DEPTH_8U, 4);
	// cvCvtColor(src, frame2, BGR2RGBA);
	// bm = Bitmap.createBitmap(frame2.width(), frame2.height(),
	// Bitmap.Config.ARGB_8888);
	// bm.copyPixelsFromBuffer(frame2.getByteBuffer());
	//
	// frame2.release();
	// ByteArrayOutputStream stream = new ByteArrayOutputStream();
	// bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
	// return stream.toByteArray();
	// }

	/**
	 * Method to crop a detected Face out of the original Image with the
	 * rectangle included For every cropped Face a face object is created and
	 * stored into an ArrayList (faceObjects)
	 * 
	 * @return IplImage the cropped face
	 */
//	private IplImage cropFace() {
//		croppedFace = new IplImage();
//		croppedFace = IplImage.create(cvGetSize(originalImage), originalImage.depth(), originalImage.nChannels());
//		cvCopy(originalImage, croppedFace);
//
//		cvResetImageROI(originalImage);
//
//		Face faceObject = new Face(croppedFace);
//		faceObjects.add(counter, faceObject);
//
//		return croppedFace;
//	}

	/**
	 * Start the camera of the users pc when the source is videosource
	 */
	protected void startCamera() {

	}

	/**
	 * Catch a frame from the videoSource
	 * 
	 * @return IplImage catched Frame
	 */
	private Mat catchFrame() {
		return null;
	}

	/**
	 * Method to output the cropped images for the user to verify the results.
	 */
//	private void showFacesAndVerify() {
//		int i = 0;
//
//		isFace = true;
//
//		for (Face f : faceObjects) {
//			cvSaveImage("NAME.jpg", f.getCroppedFace());
//			if (isFace == true) {
//				f.setFace(true);
//
//				/**
//				 * TODO: Strings setzen durch Eingabe ueber Webinterface
//				 * 
//				 */
//
//				addInformation(f, firstName, lastName, age, nationality);
//				saveImage();
//				i++;
//			} else {
//				f.setFace(false);
//				faceObjects.remove(f);
//				croppedFaces.remove(f);
//				i++;
//			}
//		}
//	}

	/**
	 * Adding additional Information about the face
	 * 
	 * @param f
	 *            Face Object
	 * @param fname
	 *            FirstName
	 * @param lname
	 *            LastName
	 * @param ag
	 *            Age
	 * @param national
	 *            Nationality
	 */
	private void addInformation(Face f, String fname, String lname, String ag, String national) {
		Face fa = f;
		fa.setFirstName(fname);
		fa.setLastName(lname);
		fa.setInfoAge(ag);
		fa.setInfoNationality(national);
	}

	/**
	 * Save the image of the detected face
	 */
	private void saveImage() {

		if (saveFace == true) {
			/**
			 * SAVE
			 */
		} else {
			/**
			 * DELETE
			 */
		}
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

}