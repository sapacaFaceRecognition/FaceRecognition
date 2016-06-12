package sapaca;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.bytedeco.javacpp.opencv_core.IplImage;

public class GenderClassification {
	private static Gender gender;
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
		genderConfidence = 0.0;
		gender = Gender.UNKNOWN;
		raceConfidence = 0.0;
		race = "";
		age = 0;
		ageRange = 0;
		ImageUploader imageUploader = new ImageUploader(image);
		String url = imageUploader.getUploadedUrl();
		httpRequest(url);
	}

	public GenderClassification() {

	}

	private static void printUsage() {
		System.console().writer().println(
				"stitching img1 img2 [...imgN]\n\n"
						+ "Flags:\n"
						+ "  --try_use_gpu (yes|no)\n"
						+ "  --output <result_img>\n"
						+ "      The default is 'result.jpg'.");
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

				String json = response.getBody().toString();

				if ((json.contains("Male") || json.contains("Female"))) {
					jsonGetGender();
					jsonGetRace();
					jsonGetAge();
				}
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}
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

	IplImage getImage() {
		return image;
	}

	String getUrlWithoutSlashes() {
		return urlWithoutSlashes;
	}

	HttpResponse<JsonNode> getResponse() {
		return response;
	}

	double getGenderConfidence() {
		return genderConfidence;
	}

	Gender getGender() {
		return gender;
	}

	double getRaceConfidence() { return raceConfidence; }

	String getRace() { return race; }

	int getAge() { return age; }

	int getAgeRange() { return ageRange; }
}
