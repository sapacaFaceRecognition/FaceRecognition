package sapaca;
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
/**
 * Created by caro on 09.05.2016.
 */
public class ImageUploaderTest {
    private ImageUploader imageUploader;
    private IplImage imageToBeUploaded;
    @Before
    public void setUp() {
        imageToBeUploaded = cvCreateImage(cvSize(210, 210), IPL_DEPTH_8U, 1);
    }

    @Test
    public void imageTest() throws Exception {
        imageUploader = new ImageUploader(imageToBeUploaded);
        assertThat(imageToBeUploaded, is(imageUploader.getImage()));
    }

}
