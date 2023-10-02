package search.tests.data.provider;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.stream.Stream;

public class DataProvider {
        public static Stream<Arguments> provideTestData() {
                return Stream.of(
                        Arguments.of(10000, 90000, Arrays.asList("Lenovo", "HP"), 12)

                );
        }
}


