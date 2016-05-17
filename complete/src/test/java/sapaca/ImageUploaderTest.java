package sapaca;
import com.cloudinary.Cloudinary;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by caro on 09.05.2016.
 */
public class ImageUploaderTest {
    private ImageUploader imageUploader;
    private IplImage imageToBeUploaded;
    @Before
    public void setUp() {
        imageToBeUploaded = cvCreateImage(cvSize(210, 210), IPL_DEPTH_8U, 1);
        imageUploader = new ImageUploader(imageToBeUploaded);
    }

    @Test
    public void imageTest() throws Exception {
        assertThat(imageToBeUploaded, is(imageUploader.getImage()));
    }

    @Test
    public void getFileNameTest() throws Exception {
        assertThat(imageUploader.getFile().getName(), is("image.jpg"));
    }

    @Test
    public void getCloudinary() throws Exception {
        Cloudinary cloudinary = imageUploader.getCloudinary();
        assertThat(cloudinary.config.cloudName, is("drrgdjljd"));
        assertThat(cloudinary.config.apiKey, is("259618711299969"));
        assertThat(cloudinary.config.apiSecret, is("y7T9lxX6GURHldFASQzbclbbutg"));
    }

    @Test
    public void urlTest() throws Exception {
        assertTrue(imageUploader.getUploadedUrl().length() > 1);
    }
}
