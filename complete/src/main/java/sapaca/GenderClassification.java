package sapaca;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.imread;
import static org.bytedeco.javacpp.opencv_highgui.imwrite;
import static org.bytedeco.javacpp.opencv_stitching.Stitcher;



public class GenderClassification {
	private static boolean tryUseGpu = false;
	private static MatVector imgs = new MatVector();
	private static String resultName = "result.jpg";
	private static Gender gender;
	private static int stitcherVar;
	private IplImage image;
	private String urlWithoutSlashes;
	private HttpResponse<JsonNode> response;
	private double genderConfidence;
	private double raceConfidence;
	private String race;
	private int age;
	private int ageRange;

	public GenderClassification(IplImage image) {
		this.image = image;
		ImageUploader imageUploader = new ImageUploader(image);
		String url = imageUploader.getUploadedUrl();
		httpRequest(url);
	}

	public GenderClassification() {

	}

	private static Gender classifyGender(int retval) {
		if (retval != 0) {
			System.out.println("Something went wrong...");
		}

		Mat pano = new Mat();
		Stitcher stitcher = Stitcher.createDefault(tryUseGpu);

		int status = stitcher.stitch(imgs, pano);

		if (status != Stitcher.OK) {
			System.out.println("Something went wrong...");
		}
		imwrite(resultName, pano);
		stitcherVar++;
		return gender;
	}

	private static void printUsage() {
		System.console().writer().println(
				"stitching img1 img2 [...imgN]\n\n"
						+ "Flags:\n"
						+ "  --try_use_gpu (yes|no)\n"
						+ "  --output <result_img>\n"
						+ "      The default is 'result.jpg'.");
	}

	private static void stitcherArgs() {
		String[] args = new String[2];
		int usageLeft = 0;
		if (args.length == 0) {
			printUsage();
			usageLeft =  -1;
		}
		for (int i = 2; i < args.length; i++) {
			if ("--help".equals(args[i])) {
				printUsage();
				usageLeft = 1;
			} else if ("--output".equals(args[i])) {
				resultName = args[i + 1];
				i++;
			} else {
				Mat img = imread(args[i]);
			}
		}
	}

	private String replaceSlashesInUrl(String url) {
		if(url.length() > 1) {
			String temp = url.replace(":", "%3A");
			urlWithoutSlashes = temp.replace("/", "%2F");
			return urlWithoutSlashes;
		}
		return "";
	}
	private void httpRequest(String uploadedImageUrl) {
		urlWithoutSlashes = replaceSlashesInUrl(uploadedImageUrl);
		if(urlWithoutSlashes.length() > 1) {
			try {
				response = Unirest.get("https://faceplusplus-faceplusplus.p.mashape.com" +
						"/detection/detect?attribute=gender%2Cage%2Crace%2Csmiling&url=" +
						urlWithoutSlashes)
						.header("X-Mashape-Key", "irY8JsoGe8msh97R53VBAlqi8FzRp10mJcrjsnvsM6bHNNcIVX")
						.header("Accept", "application/json")
						.asJson();
				if(verifyResults()) {
					jsonGetGender();
					jsonGetRace();
					jsonGetAge();
				} else{
					setAttributesForFalseClassification();
				}
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean verifyResults() {
		String json = response.getBody().toString();
		if (json.contains("Male") || json.contains("Female")) {
			return true;
		}
		return false;
	}

	private void setAttributesForFalseClassification() {
		genderConfidence = 0.0;
		gender = Gender.UNKNOWN;
		raceConfidence = 0.0;
		race = "";
		age = 0;
		ageRange = 0;
	}
	private void jsonGetGender() {
		String json = response.getBody().toString();
		String matcher = "";
		Pattern p = Pattern.compile("\"gender\":\\{\"confidence\"(.*?),");
		Matcher m = p.matcher(json);
		while (m.find()) {
			matcher = m.group(0);
		}
		String confidenceGender = matcher.substring(23, matcher.indexOf(","));
		genderConfidence = Double.parseDouble(confidenceGender);

		gender = Gender.FEMALE;
		if (json.contains("Male")) {
			gender = Gender.MALE;
		}
	}

	private void jsonGetRace() {
		String json = response.getBody().toString();
		String matcher = "";
		String matcher2 = "";
		Pattern p = Pattern.compile("\"race\":\\{\"confidence\"(.*?),");
		Matcher m = p.matcher(json);
		while (m.find()) {
			matcher = m.group(0);
		}
		String confidenceRaceString = matcher.substring(21,matcher.indexOf(","));

		raceConfidence = Double.parseDouble(confidenceRaceString);

		Pattern p1 = Pattern.compile("\"race\":\\{\"confidence\"(.*?),\"value\":\"(.*?)\"");
		Matcher m1 = p1.matcher(json);
		while (m1.find()) {
			matcher2 = m1.group(0);
		}
		int valueIndex = matcher2.indexOf("\"value\"");
		String temp = matcher2.substring(valueIndex + 9, matcher2.length() - 1);
		race = temp;
	}

	private void jsonGetAge() {
		String json = response.getBody().toString();
		String matcher = "";
		Pattern p = Pattern.compile("\"age\":(.*?)\\}");
		Matcher m = p.matcher(json);
		while (m.find()) {
			matcher = m.group(0);
		}
		String temp = matcher.substring(15, matcher.indexOf(","));
		ageRange = Integer.parseInt(temp);
		String temp2 = matcher.substring(matcher.indexOf(",") + + 9, matcher.length()-1);
		age = Integer.parseInt(temp2);
	}

	public IplImage getImage() {
		return image;
	}

	public String getUrlWithoutSlashes() {
		return urlWithoutSlashes;
	}

	public HttpResponse<JsonNode> getResponse() {
		return response;
	}

	public double getGenderConfidence() {
		return genderConfidence;
	}

	public Gender getGender() {
		return gender;
	}

	public double getRaceConfidence() { return raceConfidence; }

	public String getRace() { return race; }

	public int getAge() { return age; }

	public int getAgeRange() { return ageRange; }
}
