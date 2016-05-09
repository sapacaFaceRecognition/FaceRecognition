package sapaca;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.util.EntityUtils;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;

import java.io.File;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


		try {
			// Beispiel wie es aussehen koennte...
			URL url = Application.class.getClassLoader().getResource("TestImages/obama.jpg");
			IplImage imageToBeUploaded = cvLoadImage(new File(url.toURI()).getAbsolutePath());

			ImageUploader imageUploader = new ImageUploader(imageToBeUploaded);
		}
		catch (Exception e) {
			System.out.println("Fuck u.");
		}
	}
}
