package fizzbuss;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzTest {

    @Test
    public void get_shouldReturnNumber_givenNotMultipleOf3Or5() throws Exception {
        FizzBuzz fb = new FizzBuzz();
        assertThat(fb.get(2), is("2"));
        assertThat(fb.get(13*3-1), is("38"));
    }

    @Test
    public void fizz_shouldReturnFizz_givenMultipleOf3() throws Exception {
        FizzBuzz fb = new FizzBuzz();
        assertThat(fb.get(3), is("fizz"));
        assertThat(fb.get(13*3), is("fizz"));
    }

    @Test
    public void buzz_shouldReturnBuzz_givenMultipleOf5() throws Exception {
        FizzBuzz fb = new FizzBuzz();
        assertThat(fb.get(5), is("buzz"));
        assertThat(fb.get(13*5), is("buzz"));
    }

    @Test
    public void fizzbuzz_shouldReturnFizzBuzz_givenMultipleOf3And5() throws Exception {
        FizzBuzz fb = new FizzBuzz();
        assertThat(fb.get(15), is("fizzbuzz"));
        assertThat(fb.get(13*5*3), is("fizzbuzz"));
    }
}
