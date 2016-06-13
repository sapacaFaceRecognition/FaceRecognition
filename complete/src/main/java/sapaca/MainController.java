package sapaca;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	private MultipartFile uploadedFile;
	private boolean isUploadedImageEmpty;
	private byte[] currentImageInBytes;
	private IplImage image;
	private String imagePath;
	private PartFactory partFactory = new PartFactory();
	private static final int IS_ZERO = -42;

	@Autowired
	private FacesRepository facesRepository;

	@Autowired
	private StatisticsRepository statisticsRepository;

	private ArrayList<Face> faces;

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/home.html", method = RequestMethod.POST)
	public String home(@RequestParam(value = "inputUsername", required = true, defaultValue = "-1") String username,
			@RequestParam(value = "inputPassword", required = true, defaultValue = "-1") String password, Model model) {
		if (("admin").equals(username) && ("sapaca").equals(password)) {
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
	public String faceDetection() {
		checkStatistics();
		return "face_detection";
	}

	@RequestMapping(value = "/face_recognition.html", method = RequestMethod.GET)
	public String faceRecognition() {
		return "face_recognition";
	}

	@RequestMapping(value = "/browse_images.html", method = RequestMethod.GET)
	public String browseImage(Model model) {
		faces = (ArrayList<Face>) facesRepository.findAllByOrderByIdAsc();
		ArrayList<Long> ids = new ArrayList<>();
		for (Face currentFace : faces) {
			ids.add(currentFace.getId());
		}
		model.addAttribute("ids", ids);
		faces.clear();
		return "browse_images";
	}

	@RequestMapping(value = "/about_us.html", method = RequestMethod.GET)
	public String aboutUs() {
		return "about_us";
	}

	@RequestMapping(value = "/statistics.html", method = RequestMethod.GET)
	public String statistics(Model model) {
		model.addAttribute("counted_images", facesRepository.count());

		String noDataAvailable = "No Data available";
		checkStatistics();
		Statistics statistics = statisticsRepository.findById(0);

		double averageAge = statistics.calculateAverageAge();
		if (averageAge != IS_ZERO) {
			model.addAttribute("average_age", averageAge);
		} else {
			model.addAttribute("average_age", noDataAvailable);
		}

		double accuracyOfCalculation = statistics.calculateAccuracyOfCalculation();
		if (accuracyOfCalculation != IS_ZERO) {
			model.addAttribute("accuracy_of_calculation", accuracyOfCalculation);
		} else {
			model.addAttribute("accuracy_of_calculation", noDataAvailable);
		}

		long averageCalculationTime = statistics.calculateAverageCalculationTime();
		if (averageCalculationTime != IS_ZERO) {
			model.addAttribute("average_calculation_time", averageCalculationTime + "ms");
		} else {
			model.addAttribute("average_calculation_time", noDataAvailable);
		}

		model.addAttribute("gender_male", facesRepository.findByGender(Gender.MALE).size());
		model.addAttribute("gender_female", facesRepository.findByGender(Gender.FEMALE).size());
		model.addAttribute("gender_unknown", facesRepository.findByGender(Gender.UNKNOWN).size());

		HashMap<String, Integer> nationalityDistribution = calculateNationalityDistribution();

		model.addAttribute("location_germany", nationalityDistribution.get("germany"));
		model.addAttribute("location_england", nationalityDistribution.get("england"));
		model.addAttribute("location_usa", nationalityDistribution.get("usa"));
		model.addAttribute("location_france", nationalityDistribution.get("france"));
		return "statistics";
	}

	@RequestMapping(value = "/face_detection.html", method = RequestMethod.POST)
	public String uploadImage(@RequestParam(value = "uploadedFile", required = false) MultipartFile uploadedFile,
			Model model) throws IOException, ServletException {

		Part part = partFactory.load(PartToDetect.FACE);

		// ... do whatever you want with 'myFile'
		// Redirect to a successful upload page
		this.uploadedFile = uploadedFile;

		isUploadedImageEmpty = uploadedFile.isEmpty() ? true : false;
		if (!isUploadedImageEmpty) {
			currentImageInBytes = uploadedFile.getBytes();
			String imageParam = saveFile();
			// String fileName =
			// imageParam.split("/")[imageParam.split("/").length - 1];
			// imagePath = fileName;
			// if (System.getProperty("os.name").contains("indow")) {
			// imagePath = imagePath.substring(1, imagePath.length());
			// }
			imagePath = imageParam;
			System.out.println("ip: " + getImagePath());
			
			image = cvLoadImage(getImagePath(), 1);
			long timeInMillis = System.currentTimeMillis();
			Detector detection = new Detector(part, image);
			long calculationTime = System.currentTimeMillis() - timeInMillis;

			Statistics statistics = statisticsRepository.findById(0);
			ArrayList<Long> calculationTimeArrayList = statistics.getCalculationTime();
			calculationTimeArrayList.add(calculationTime);
			statisticsRepository.save(statistics);

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
	}

	@RequestMapping(value = "/test.html", method = RequestMethod.POST)
	public String test(RedirectAttributes model) {
		System.out.println("test");
		model.addFlashAttribute("test", "test");
		return "test123";
	}

	@RequestMapping(value = "/next_face.html", method = RequestMethod.POST)
	public String nextFace(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "nationality", required = false) String nationality,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "faceDetected", required = false) String faceDetected,
			@RequestParam(value = "noFaceDetected", required = false) String noFaceDetected,
			@RequestParam(value = "genderClassification", required = false) String genderClassification,
			@RequestParam(value = "eyeDetection", required = false) String eyeDetection,
			@RequestParam(value = "attributes", required = false) String attributes, RedirectAttributes model) {

		if (genderClassification != null) {
			Gender classifiedGender = new GenderClassification(faces.get(0).getCroppedFace()).getGender();
			model.addFlashAttribute("is_face_detected", "true");
			model.addFlashAttribute("classified_gender", classifiedGender.toString());
		}
		if (eyeDetection != null) {
			Part eyePart = new PartFactory().load(PartToDetect.EYES);
			Detector detector = new Detector(eyePart, faces.get(0).getCroppedFace());
			model.addFlashAttribute("is_face_detected", "true");
			Face detectedEye = new Face(detector.getEyeImage());
			detectedEye.setDbImage(convertIplImageToByteArray(detectedEye));
			faces.add(0, detectedEye);
		}

		if (attributes != null) {
			model.addFlashAttribute("attributes_expanded", "true");
			GenderClassification genderClass = new GenderClassification(faces.get(0).getCroppedFace());
			model.addFlashAttribute("classified_gender", genderClass.getGender().toString());
			model.addFlashAttribute("gender_confidence", genderClass.getGenderConfidence());
			model.addFlashAttribute("age", genderClass.getAge());
			model.addFlashAttribute("age_range", genderClass.getAgeRange());
			model.addFlashAttribute("race", genderClass.getRace());
			model.addFlashAttribute("race_confidence", genderClass.getRaceConfidence());
			Statistics statistics = statisticsRepository.findById(0);
			ArrayList<Integer> ages = statistics.getAges();
			ages.add(genderClass.getAge());
			statisticsRepository.save(statistics);
		}

		if (faces != null && !faces.isEmpty()) {
			if (faceDetected != null) {
				Face currentFace = faces.get(0);
				if (firstName != null) {
					currentFace.setFirstName(firstName);
				}
				if (lastName != null) {
					currentFace.setLastName(lastName);
				}
				if (age != null) {
					currentFace.setAge(age);
				}
				if (nationality != null) {
					currentFace.setNationality(nationality);
				}
				if (gender != null) {
					switch (gender) {
					case "männlich":
						currentFace.setGender(Gender.MALE);
						break;
					case "weiblich":
						currentFace.setGender(Gender.FEMALE);
						break;
					case "unbekannt":
						currentFace.setGender(Gender.UNKNOWN);
						break;
					default:
						break;
					}
				}
				if (location != null) {
					currentFace.setLocation(location);
				}
				facesRepository.save(faces.get(0));
				faces.remove(0);

				Statistics statistics = statisticsRepository.findById(0);
				int isFace = statistics.getIsFace();
				isFace += 1;
				statistics.setIsFace(isFace);
				statisticsRepository.save(statistics);

			} else if (noFaceDetected != null) {
				faces.remove(0);
				Statistics statistics = statisticsRepository.findById(0);
				int isNoFace = statistics.getIsNoFace();
				isNoFace += 1;
				statistics.setIsNoFace(isNoFace);
				statisticsRepository.save(statistics);
			}

			if (faces.isEmpty()) {
				model.addFlashAttribute("is_face_detected", "false");
				model.addFlashAttribute("faces_empty", "true");
			} else {
				model.addFlashAttribute("is_face_detected", "true");
			}
		} else {
			model.addFlashAttribute("isFaceDetected", "false");
		}

		return "redirect:/face_detection.html";
	}

	@RequestMapping(value = "get_current_image", produces = MediaType.IMAGE_PNG_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public synchronized ResponseEntity<byte[]> getCurrentImage(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("getCurrentImage... ");
		if (id != null) {
			byte[] imageContent = facesRepository.findById(id).getDbImage();
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
		} else if (faces != null && !faces.isEmpty()) {
			byte[] imageContent = faces.get(0).getDbImage();
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
		} else if (currentImageInBytes != null) {
			byte[] imageContent = currentImageInBytes;
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
		} else {
			byte[] imageContent = getImage("static/pic/Detecting.png");
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
		}
	}

	// Should be DELETE (not supported by html)
	@RequestMapping(value = "/delete_image.html", method = RequestMethod.GET)
	public String deleteImage(@RequestParam(value = "id", required = true) long id) {
		facesRepository.delete(facesRepository.findById(id));
		return "redirect:/browse_images.html";
	}

	public byte[] getImage(String filePath) {
		byte[] data = {};
		try {
			String currentImagePath = filePath;
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(currentImagePath);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			IOUtils.copy(inputStream, outputStream);
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
			data = outputStream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	private String saveFile() {
		new File("uploaded_images/").mkdir();
		File imagePathFile = new File("uploaded_images/image_" + System.currentTimeMillis() + ".jpg").getAbsoluteFile();
		System.out.println("ImagePath: " + imagePathFile.toString());
		if (!imagePathFile.getParentFile().exists()) {
			imagePathFile.getParentFile().mkdir();
		}
		try {
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imagePathFile));
			outputStream.write(uploadedFile.getBytes());
			outputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imagePathFile.getAbsolutePath();
	}

	private byte[] convertIplImageToByteArray(Face face) {
		try {
			BufferedImage bufferedImage = face.getCroppedFace().getBufferedImage();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new byte[0];
	}

	private String getImagePath() {
		return imagePath;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		faces = (ArrayList<Face>) facesRepository.findAllByOrderByIdAsc();
		return "home";
	}

	private void checkStatistics() {
		if (statisticsRepository.findById(0) == null) {
			Statistics statistics = new Statistics();
			statistics.initialize();
			statisticsRepository.save(statistics);
		}
	}

	private HashMap<String, Integer> calculateNationalityDistribution() {
		HashMap<String, Integer> nationalityDistribuion = new HashMap<>();
		int germany = 0, england = 0, usa = 0, france = 0;
		for (Face face : facesRepository.findAll()) {
			String nationality = face.getNationality();
			if (nationality == null) {
			} else if (nationality.equals("Deutschland")) {
				germany += 1;
			} else if (nationality.equals("England")) {
				england += 1;
			} else if (nationality.equals("USA")) {
				usa += 1;
			} else if (nationality.equals("Frankreich")) {
				france += 1;
			}
		}
		nationalityDistribuion.put("germany", germany);
		nationalityDistribuion.put("england", england);
		nationalityDistribuion.put("usa", usa);
		nationalityDistribuion.put("france", france);
		return nationalityDistribuion;
	}

}
