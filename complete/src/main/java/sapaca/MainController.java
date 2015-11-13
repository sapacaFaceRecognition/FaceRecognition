package sapaca;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
	
	private MultipartFile myFile;
	private Boolean isMyFileFilled;
	
	public MainController() {
		isMyFileFilled = false;
	}

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

	@RequestMapping(value = "/face_detection.html", headers = "content-type=application/x-www-form-urlencoded, multipart/*", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("myFile") MultipartFile myFile) throws IOException, ServletException {
		// ... do whatever you want with 'myFile'
		// Redirect to a successful upload page
		this.myFile = myFile;
		refreshMyFileBoolean();
		System.out.println(myFile.getOriginalFilename());
		return "face_detection";
	}
	
	public void refreshMyFileBoolean(){
		if(myFile.isEmpty()){
			isMyFileFilled = false;
		}else{
			isMyFileFilled = true;
		}
	}
}
