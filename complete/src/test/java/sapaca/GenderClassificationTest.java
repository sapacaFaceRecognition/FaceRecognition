package sapaca;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by caro on 18.05.2016.
 */
public class GenderClassificationTest {

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
}
