package sapaca;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by caro on 18.05.2016.
 */
public class GenderClassificationTest {
    private GenderClassification genderClassification;

    @Before
    public void setUp() throws Exception {
        genderClassification = new GenderClassification();
    }
    @Test
    public void EnumMaleTest() throws Exception {
        assertThat(Gender.valueOf("MALE"), is(notNullValue()));
    }

    @Test
    public void EnumFemaleTest() throws Exception {
        assertThat(Gender.valueOf("FEMALE"), is(notNullValue()));
    }

    @Test
    public void EnumUnknownTest() throws Exception {
        assertThat(Gender.valueOf("UNKNOWN"), is(notNullValue()));
    }

    @Test
    public void getAgeErrorTest() {
        assertThat(genderClassification.getAge(), is(0));
    }

    @Test
    public void getAgeRangeErrorTest() {
        assertThat(genderClassification.getAge(), is(0));
    }

    @Test
    public void getRaceeErrorTest() {
        assertThat(genderClassification.getRace(), is(nullValue()));
    }

    @Test
    public void getRaceeConfidenceErrorTest() {
        assertThat(genderClassification.getRaceConfidence(), is(0.0));
    }

    @Test
    public void getGenderErrorTest() {
        assertThat(genderClassification.getGender(), is(nullValue()));
    }

    @Test
    public void getGenderConfidenceErrorTest() {
        assertThat(genderClassification.getGenderConfidence(), is(0.0));
    }

    @Test
    public void getResponseErrorTest() {
        assertThat(genderClassification.getResponse(), is(nullValue()));
    }

    @Test
    public void getUrlErrorTest() {
        assertThat(genderClassification.getUrlWithoutSlashes(), is(nullValue()));
    }

    @Test
    public void getImageErrorTest() {
        assertThat(genderClassification.getImage(), is(nullValue()));
    }
}
