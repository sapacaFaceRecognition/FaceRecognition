package sapaca;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by caro on 09.05.2016.
 */
public class ImageUploader {
    private Cloudinary cloudinary;

    //public ImageUploader(String filePath) {
    public ImageUploader() {

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drrgdjljd",
                "api_key", "259618711299969",
                "api_secret", "y7T9lxX6GURHldFASQzbclbbutg"));

        // File file = new File(filePath);
        File file = new File("C:\\Users\\caro\\IdeaProjects\\FaceRecognition\\complete\\src\\main\\resources\\TestImages\\kurtcobain.jpg");
        uploadImage(file);
    }

    private String uploadImage(File file) {
        String uploadedUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", "url"));
            uploadedUrl = (String) uploadResult.get("url");
            System.out.println(uploadedUrl);
        } catch (IOException e) {
            uploadedUrl = "";
            e.printStackTrace();
        }
        return uploadedUrl;
    }
}
