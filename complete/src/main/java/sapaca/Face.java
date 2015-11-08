
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
	private boolean isFace;
	private boolean saveImage;

	
	protected Face(IplImage croppedFace){
		this.croppedFace = croppedFace;
		
		addInfo();
	}
	
	private void addInfo() {
		if (isFace == true) {
			firstName = "";
			lastName = "";
			age = "";
			nationality = "";
			
			saveImage();
		}
	}
	
	
	private void saveImage() {
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
