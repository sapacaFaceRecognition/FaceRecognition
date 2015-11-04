package sapaca;
import static org.bytedeco.javacpp.opencv_core.CV_AA;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRectangle;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;

@Entity
@Table(name="faces")
public class Face {
	
	@Id
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="age")
	private String age;
	
	@Column(name="nationality")
	private String nationality;
	private IplImage temp;
	
	@Column(name="image")
	private IplImage croppedFace;
	private CvRect r;
	private boolean saveImage = true;
	
	protected Face(){
	}
	
	private void addInfo() {
		firstName = "";
		lastName = "";
		age = "";
		nationality = "";
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String infoFirstname) {
		this.firstName = infoFirstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String infoLastname) {
		this.lastName = infoLastname;
	}

	public String getInfoAge() {
		return age;
	}

	public void setInfoAge(String infoAge) {
		this.age = infoAge;
	}

	public String getInfoNationality() {
		return nationality;
	}

	public void setInfoNationality(String infoNationality) {
		this.nationality = infoNationality;
	}
}
