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

import org.bytedeco.javacpp.FlyCapture2.Image;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

/**
 * Class used to detect faces from an image or video source.
 * @author caro
 * 
 */
public class FaceDetection {
	
	private int detectedFaces;
	private String originalImagePath;
	private String saveImagePath;
	private IplImage originalImage;
	private IplImage grayImage;
	private IplImage croppedFace;
	private ArrayList<Face> faceObjects;
	private int counter;
 
    private static final String XML_FILE =	 "C:\\Users\\caro\\Documents\\GitHub\\FaceDetection\\src\\"
    										+ "haarcascade_frontalface_alt.xml";
    
    FaceDetection(String originalImagePath, String saveImagePath) {
    	this.setOriginalImagePath(originalImagePath);
    	this.setSaveImagePath(saveImagePath);
    	detect();
    }
    
    /**
     * Detect faces from an image and draw rectangles around
     * the detected faces at the original image. 
     */
    private void detect() {
    	
    	originalImage = cvLoadImage(getOriginalImagePath(), 1);
    	grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U,1);
    	cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);
    	
    	CvMemStorage storage = CvMemStorage.create();
    	
    	// Instantiate classifier cascade
    	CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(XML_FILE));
    	
    	CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, 2, 0);
    	
    	// Draw rectangles (on original image)
        for (int i = 0; i < faces.total(); i++) {
        	counter = i;
            CvRect r = new CvRect(cvGetSeqElem(faces, i));
            cvRectangle(originalImage, cvPoint(r.x(), r.y()),
                    cvPoint(r.x() + r.width(), r.y() + r.height()),
                    CvScalar.GREEN, 1, CV_AA, 0);
            
            CvRect r2 = cvRect(r.x() - 100, r.y() - 100, r.width() + 200, r.height() + 200);
            cvSetImageROI(originalImage, r2);
            liste.add(i,cropFace());
        }

        detectedFaces = faces.total();

        //cvSaveImage(getSaveImagePath(), originalImage);
        System.out.println(detectedFaces);
    	
    }

    
    private static byte[] IplImageToByteArray(IplImage src) {
    	Bitmap bm = null;
    	int width = src.width();
    	int height = src.height();

    	IplImage frame2 = IplImage.create(width, height, IPL_DEPTH_8U, 4);
    	cvCvtColor(src, frame2, BGR2RGBA);
    	bm = Bitmap.createBitmap(frame2.width(), frame2.height(), Bitmap.Config.ARGB_8888);
    	bm.copyPixelsFromBuffer(frame2.getByteBuffer());
    	
    	frame2.release();
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
    	return stream.toByteArray();
    }
    
    /**
     * Method to crop a detected Face out of the original Image
     * with the rectangle included
     * For every cropped Face a face object is created and stored
     * into an ArrayList (faceObjects)
     * @return IplImage the cropped face
     */
    private IplImage cropFace() {
    	
    	croppedFace = new IplImage();
    	croppedFace = IplImage.create(cvGetSize(originalImage), originalImage.depth(), originalImage.nChannels());
    	cvCopy(originalImage, croppedFace);
    	
    	cvResetImageROI(originalImage);
    	
    	Face face = new Face(croppedFace);
    	faceObjects.add(counter, face);
    	
    	return croppedFace;
    }
    /**
     * 
     */
    protected void startCamera() {
    	
    }
    
    /**
     * 
     * @return
     */
    private Image catchFrame() {
		return null;
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