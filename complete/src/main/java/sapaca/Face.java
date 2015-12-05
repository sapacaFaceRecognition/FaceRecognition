package sapaca;

import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplImage;

@Entity
@Table(name = "faces")
public class Face {

	@Id
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "age")
	private String age;

	@Column(name = "nationality")
	private String nationality;

	@Transient
	private IplImage temp;

	@Column(name = "image", columnDefinition = "longblob")
	private byte[] dbImage;

	@Transient
	private IplImage croppedFace;

	@Transient
	private CvRect r;

	@Transient
	private boolean isFace;

	@Transient
	private boolean saveImage;

	protected Face(IplImage croppedFace) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isFace() {
		return isFace;
	}

	public void setFace(boolean isFace) {
		this.isFace = isFace;
	}

	public boolean isSaveImage() {
		return saveImage;
	}

	public void setSaveImage(boolean saveImage) {
		this.saveImage = saveImage;
	}

	public IplImage getCroppedFace() {
		return croppedFace;
	}

	public void setCroppedFace(IplImage croppedFace) {
		this.croppedFace = croppedFace;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public byte[] getDbImage() {
		return dbImage;
	}

	public void setDbImage(byte[] dbImage) {
		this.dbImage = dbImage;
	}

}
