package sapaca;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;

public class Face {
	private String infoFirstname;
	private String infoLastname;
	private String infoAge;
	private String infoNationality;
	private IplImage temp;
	private IplImage croppedFace;
	private CvRect r;
	private boolean saveImage = true;
	
	Face(){
		
	}
	
	private void addInfo() {
		infoFirstname = "";
		infoLastname = "";
		infoAge = "";
		infoNationality = "";
	}
	
	public IplImage cropFace(IplImage originalImage, int rwidth, int rheight, int rx, int ry) {
        cvCopy(originalImage, temp);
        r = new CvRect();
        cvRectangle(originalImage, cvPoint(rx, ry),
                cvPoint(rx + rwidth, ry + rheight),
                CvScalar.GREEN, 1, CV_AA, 0);
        cvSetImageROI(temp, r);
        croppedFace = IplImage.create(r.width(), r.height(), IPL_DEPTH_8U, 1);
        cvCopy(temp, croppedFace);
        
        saveImage(croppedFace);
        
		return croppedFace;
	}
	
	private void saveImage(IplImage croppedFace) {
		if (saveImage == true) {
			cvSaveImage("PATH", croppedFace);
		}
	}

	public String getInfoFirstname() {
		return infoFirstname;
	}

	public void setInfoFirstname(String infoFirstname) {
		this.infoFirstname = infoFirstname;
	}

	public String getInfoLastname() {
		return infoLastname;
	}

	public void setInfoLastname(String infoLastname) {
		this.infoLastname = infoLastname;
	}

	public String getInfoAge() {
		return infoAge;
	}

	public void setInfoAge(String infoAge) {
		this.infoAge = infoAge;
	}

	public String getInfoNationality() {
		return infoNationality;
	}

	public void setInfoNationality(String infoNationality) {
		this.infoNationality = infoNationality;
	}
}
