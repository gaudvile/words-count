import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class WordsCountingApplicationTest {

    WordsCountingApplication app = new WordsCountingApplication();

    @Test
    public void handleHyphensTest() {
        String withHyphen = "-hello-";
        String expected = "hello";
        String actual = WordsCountingApplication.handleHyphenAndQuotes(withHyphen);
        assertThat(actual.equals(expected));
    }

    @Test
    public void handleQuotesTest() {
        String withHyphen = "'hello'";
        String expected = "hello";
        String actual = WordsCountingApplication.handleHyphenAndQuotes(withHyphen);
        assertThat(actual.equals(expected));
    }

    @Test
    public void expectNoFileException(){

        String fakeFile = "test.txt";

        Assertions.assertThatThrownBy(() -> WordsCountingApplication.putWordsToMap(fakeFile))
                .isInstanceOf(IOException.class).hasMessageContaining("The system cannot find the file specified");
    }
}
