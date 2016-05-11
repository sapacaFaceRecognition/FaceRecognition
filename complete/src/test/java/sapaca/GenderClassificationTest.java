package sapaca;

import org.apache.juli.FileHandler;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
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
            URL url = GenderClassificationTest.class.getClassLoader().getResource("obama.jpg");
            System.out.println(url.toString());
            System.out.println(new File(url.toURI()).getAbsolutePath());
            if (url != null) {
                image = cvLoadImage(new File(url.toURI()).getAbsolutePath());
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

    @Test
    public void replaceSlashesInUrlTest() throws Exception {
//        genderClassification = new GenderClassification(image);
//        genderClassification.replaceSlashesInUrl("http://res.cloudinary.com/drrgdjljd/image/upload/v1462817365/ehozbefqtg1vywlp4fwq.jpg");
//        assertThat("http%3A%2F%2Fres.cloudinary.com%2Fdrrgdjljd%2Fimage%2Fupload%2Fv1462817365%2Fehozbefqtg1vywlp4fwq.jpg",
//                        is(genderClassification.getUrlWithoutSlashes()));
    }
}
