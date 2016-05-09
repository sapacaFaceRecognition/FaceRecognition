package sapaca;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
/**
 * Created by caro on 09.05.2016.
 */
public class ImageUploaderTest {
    private ImageUploader imageUploader;
    private IplImage imageToBeUploaded;
    @Before
    public void setUp() {
        URL url = ImageUploaderTest.class.getClassLoader().getResource("TestImages/obama.jpg");
        try {
            imageToBeUploaded = cvLoadImage(new File(url.toURI()).getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        imageUploader = new ImageUploader(imageToBeUploaded);
    }

    @Test
    public void imageTest() throws Exception {
        assertThat(imageToBeUploaded, is(imageUploader.getImage()));
    }
}
