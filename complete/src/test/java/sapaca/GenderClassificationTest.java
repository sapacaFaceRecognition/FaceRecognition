package sapaca;

import org.apache.juli.FileHandler;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by caro on 09.05.2016.
 */
public class GenderClassificationTest {
    private IplImage image;
    private GenderClassification genderClassification;

    @Before
    public void setUp() {
        try {
            URL url = this.getClass().getClassLoader().getResource("obama.jpg");
            //InputStream in = getClass().getResourceAsStream("/TestImages/obama.jpg");
            if (url.toString().length() > 1) {
                image = cvLoadImage(new File(url.toURI()).getAbsolutePath());
                ImageUploader imageUploader = new ImageUploader(image);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void loadImageTest() {
//        URL resourceURL = FileHandler.class.getClassLoader().getResource("TestImages/cwurst.jpg");
//        try {
//            image = cvLoadImage(new File(resourceURL.toURI()).getAbsolutePath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        if(resourceURL == null)
//            try {
//                throw new FileNotFoundException("\"TestImages/cwurst.jpg\" not found");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        genderClassification = new GenderClassification(image);
//        assertThat(image, is(genderClassification.getImage()));
    }
}
