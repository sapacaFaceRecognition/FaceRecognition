package sapaca;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

	private MultipartFile uploadedFile;
	private boolean isUploadedImageEmpty;

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
	public String uploadImage(@RequestParam("uploadedFile") MultipartFile uploadedFile, Model model)
			throws IOException, ServletException {
		// ... do whatever you want with 'myFile'
		// Redirect to a successful upload page
		this.uploadedFile = uploadedFile;
		System.out.println(uploadedFile.getOriginalFilename());
		isUploadedImageEmpty = uploadedFile.isEmpty() ? true : false;
		if (!isUploadedImageEmpty) {
<<<<<<< HEAD
			String fileName = saveFile();
			String fullFileName = fileName.split("/")[fileName.split("/").length - 1];
			String fullImagePath = getClass().getClassLoader().getResource(".").getFile() + "uploaded_images/"
					+ fullFileName;
			if (System.getProperty("os.name").contains("indow")) {
				fullImagePath = fullImagePath.substring(1, fullImagePath.length());
			}
			String newFullImagePath = fullImagePath.replace(".jpg", "_face.jpg");
			new FaceDetection(fullImagePath, newFullImagePath);
			model.addAttribute("image_path",
					fileName.split("/")[fileName.split("/").length - 1].replace(".jpg", "_face.jpg"));
=======
			String test = saveFile();
			 String fileName = test.split("/")[test.split("/").length - 1];
			 String imagePath =
			 getClass().getClassLoader().getResource(".").getFile() +
			 "uploaded_images/" + fileName;
			 if (System.getProperty("os.name").contains("indow")) {
			 imagePath = imagePath.substring(1, imagePath.length());
			 }
			 String newImagePath = imagePath.replace(".jpg", "_face.jpg");
			 new FaceDetection(imagePath, newImagePath);
			model.addAttribute("image_path", test.replace(".jpg", "_face.jpg"));
>>>>>>> refs/remotes/origin/playground
		}
		return "face_detection";
		//return test.replace(".jpg", "_face.jpg")
	}

	@RequestMapping(value = "/getImage/{imageId}")
	@ResponseBody
	public byte[] getImage(@PathVariable String imageId) {
		byte[] data = {};
		try {
			String imagePath = getClass().getClassLoader().getResource(".").getFile() + "/uploaded_images/" + imageId
					+ ".jpg";
			Path path;
			if (System.getProperty("os.name").contains("indow")) {
				path = Paths.get(imagePath.substring(1, imagePath.length()));
			} else {
				path = Paths.get(imagePath);
			}
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
}
