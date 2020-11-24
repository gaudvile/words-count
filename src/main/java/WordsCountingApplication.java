import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class WordsCountingApplication {

    static Map<String, Integer> wordsMap = new HashMap<>();

    public static void main(final String[] args) {

        try {
            putWordsToMap("hp_part1.txt");
            putWordsToMap("hp_part2.txt");
            putWordsToMap("hp_part3.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        startThread("wordsAG.txt", wordsMap, "^[a-g].*$");
        startThread("wordsHN.txt", wordsMap, "^[h-n].*$");
        startThread("wordsOU.txt", wordsMap, "^[o-u].*$");
        startThread("wordsVZ.txt", wordsMap, "^[v-z].*$");

    }

    private static void startThread(String fileNameTo, Map<String, Integer> wordsMap, String regex) {
        FilteringService filteringService = new FilteringService(fileNameTo, regex, wordsMap);
        Thread split = new Thread(filteringService);
        split.start();
    }

    public static String handleHyphenAndQuotes(String word) {
        if (word.equals("--") || word.equals("-")) {
            return null;
        }
        if (word.startsWith("-") || word.startsWith("'")) {
            word = word.substring(1);
        }
        if (word.endsWith("-") || word.endsWith("'")) {
            word = word.substring(0, word.length() - 1);
        }
        return word;
    }

    public static void putWordsToMap(String fileName) throws IOException {
        String line;
        String punctuation = " ,.;:\"()!?~";
        BufferedReader reader_one = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        while ((line = reader_one.readLine()) != null) {
            StringTokenizer words = new StringTokenizer(line, punctuation);
            while (words.countTokens() != 0) {
                String word = handleHyphenAndQuotes(words.nextToken().toLowerCase());
                if (word != null) {
                    if (wordsMap.containsKey(word)) {
                        wordsMap.replace(word, wordsMap.get(word) + 1);
                    } else {
                        wordsMap.put(word, 1);
                    }
                }
            }
        }
        reader_one.close();
    }
}
