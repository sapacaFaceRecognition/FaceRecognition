package sapaca;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.bytedeco.javacpp.opencv_core.IplImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Created by caro on 09.05.2016.
 */
public class ImageUploader {
    private Cloudinary cloudinary;
    private IplImage imageToBeUploaded;
    private BufferedImage buffImage;
    private File outputFile;

    public ImageUploader(IplImage imageToBeUploaded) {
        this.imageToBeUploaded = imageToBeUploaded;
        setCloudinary();
        uploadImage(IplImageToFile());
    }

    private void setCloudinary() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drrgdjljd",
                "api_key", "259618711299969",
                "api_secret", "y7T9lxX6GURHldFASQzbclbbutg"));
    }

    private File IplImageToFile() {
        buffImage = imageToBeUploaded.getBufferedImage();
        outputFile = new File("image.jpg");
        try {
            ImageIO.write(buffImage, "jpg", outputFile);
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String uploadImage(File file) {
        if (file == null) {
            return "";
        }

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

    private String getURL(String nameOfFile) {
        try {
            URL url = ImageUploader.class.getClassLoader().getResource(nameOfFile);
            System.out.println(new File(url.toURI()).getAbsolutePath());
            return new File(url.toURI()).getAbsolutePath();
        }
        catch (URISyntaxException e) {
            System.out.println("Source not found.");
            return "";
        }
        catch (Exception e) {
            System.out.println("Something went horribly wrong...");
            return " ";
        }
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }

    public IplImage getImage() {
        return imageToBeUploaded;
    }
}
