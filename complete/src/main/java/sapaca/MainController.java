package sapaca;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

	private MultipartFile uploadedFile;
	private boolean isUploadedImageEmpty;
	private byte[] currentImageInBytes;

	@Autowired
	private FacesRepository facesRepository;

	private ArrayList<Face> faces;

	// @RequestMapping("/test")
	// public String test(@RequestParam(value = "name", required = false,
	// defaultValue = "World") String name,
	// Model model) {
	// model.addAttribute("name", name);
	// return "test";
	// }

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/home.html", method = RequestMethod.POST)
	public String home(@RequestParam(value = "inputUsername", required = true, defaultValue = "-1") String username,
			@RequestParam(value = "inputPassword", required = true, defaultValue = "-1") String password, Model model) {
		if (username.equals("admin") && password.equals("sapaca")) {
			return "home";
		} else {
			model.addAttribute("username", username);
			return "login";
		}
	}

	@RequestMapping(value = "/home.html", method = RequestMethod.GET)
	public String homeWithoutAuthentication() {
		return "home";
	}

	@RequestMapping(value = "/face_detection.html", method = RequestMethod.GET)
	public String faceDetection(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			Model model) {
		return "face_detection";
	}

	@RequestMapping(value = "/face_recognition.html", method = RequestMethod.GET)
	public String faceRecognition(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			Model model) {
		return "face_recognition";
	}

	@RequestMapping(value = "/browse_image.html", method = RequestMethod.GET)
	public String browseImage(Model model) {
		return "browse_image";
	}

	@RequestMapping(value = "/about_us.html", method = RequestMethod.GET)
	public String aboutUs(Model model) {
		return "about_us";
	}

	@RequestMapping(value = "/face_detection.html", method = RequestMethod.POST)
	public String uploadImage(@RequestParam(value = "uploadedFile", required = false) MultipartFile uploadedFile,
			Model model) throws IOException, ServletException {
		// ... do whatever you want with 'myFile'
		// Redirect to a successful upload page
		this.uploadedFile = uploadedFile;
		System.out.println(uploadedFile.getOriginalFilename());
		isUploadedImageEmpty = uploadedFile.isEmpty() ? true : false;
		if (!isUploadedImageEmpty) {
			currentImageInBytes = uploadedFile.getBytes();
			String imageParam = saveFile();
			String fileName = imageParam.split("/")[imageParam.split("/").length - 1];
			String imagePath = getClass().getClassLoader().getResource(".").getFile() + "uploaded_images/" + fileName;
			if (System.getProperty("os.name").contains("indow")) {
				imagePath = imagePath.substring(1, imagePath.length());
			}
			String newImagePath = imagePath.replace(".jpg", "_face.jpg");
			FaceDetection detection = new FaceDetection(imagePath, newImagePath);
			if (faces != null) {
				faces.clear();
			}
			faces = detection.getFaces();
			if (faces != null && !faces.isEmpty()) {
				for (Face currentFace : faces) {
					currentFace.setDbImage(convertIplImageToByteArray(currentFace));
				}
				model.addAttribute("is_face_detected", "true");
				model.addAttribute("image_path", imageParam.replace(".jpg", "_face.jpg"));
				System.out.println("Face detected and saved;");
			} else {
				model.addAttribute("isFaceDetected", "false");
			}

		}
		model.addAttribute("isFaceDetected", "false");
		return "face_detection";
		// return test.replace(".jpg", "_face.jpg")
	}

	@RequestMapping(value = "/next_face.html", method = RequestMethod.POST)
	public String nextFace(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "nationality", required = false) String nationality,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "faceDetected", required = false) String faceDetected,
			@RequestParam(value = "noFaceDetected", required = false) String noFaceDetected, Model model) {

		System.out.println(firstName + ", " + lastName + ", " + age + ", " + nationality + ", " + location + ", "
				+ faceDetected + ", " + noFaceDetected);

		if (faces != null && !faces.isEmpty()) {
			if (faceDetected != null) {
				Face currentFace = faces.get(0);
				currentFace.setFirstName(firstName);
				currentFace.setLastName(lastName);
				currentFace.setAge(age);
				currentFace.setNationality(nationality);
				currentFace.setLocation(location);
				facesRepository.save(faces.get(0));
				faces.remove(0);
			} else if (noFaceDetected != null) {
				faces.remove(0);
			}

			if (faces.isEmpty()) {
				model.addAttribute("is_face_detected", "false");
				model.addAttribute("faces_empty", "true");
			} else {
				model.addAttribute("is_face_detected", "true");
			}
		} else {
			model.addAttribute("isFaceDetected", "false");
		}

		return "face_detection";
	}

	@RequestMapping(value = "get_current_image", produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getCurrentImage() {
		if (faces != null && !faces.isEmpty()) {
			byte[] imageContent = faces.get(0).getDbImage();
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
		} else if (currentImageInBytes != null) {
			byte[] imageContent = currentImageInBytes;
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
		} else {
			byte[] imageContent = getImage("./static/pic/Detecting.png");
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
		}
	}

	public byte[] getImage(String filePath) {
		byte[] data = {};
		try {
			String imagePath = filePath;
			Path path = Paths.get(imagePath);
			// if (System.getProperty("os.name").contains("indow")) {
			// path = Paths.get(imagePath.substring(1, imagePath.length()));
			// } else {
			// path = Paths.get(imagePath);
			// }
			data = Files.readAllBytes(path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	private String saveFile() {
		File imagePath = new File(getClass().getClassLoader().getResource(".").getFile() + "/uploaded_images/image_"
				+ System.currentTimeMillis() + ".jpg");
		System.out.println("ImagePath: " + imagePath.toString());
		if (!imagePath.getParentFile().exists()) {
			imagePath.getParentFile().mkdir();
		}
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imagePath));
			outputStream.write(uploadedFile.getBytes());
			outputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "getImage/" + imagePath.getName();
	}

	private byte[] convertIplImageToByteArray(Face face) {
		try {
			BufferedImage bufferedImage = face.getCroppedFace().getBufferedImage();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			byte[] imageAsByteArray = byteArrayOutputStream.toByteArray();
			return imageAsByteArray;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void convertByteArrayToIplImage(Face face) {
		try {
			InputStream in = new ByteArrayInputStream(face.getDbImage());
			BufferedImage bufferedImage = ImageIO.read(in);
			IplImage iplImage = new IplImage().createFrom(bufferedImage);
			face.setCroppedFace(iplImage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}