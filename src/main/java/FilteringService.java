import lombok.AllArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class FilteringService implements Runnable {

    String fileName;
    String regex;

    Map<String, Integer> allWords;

    @Override
    public void run() {
        Map<String, Integer> alphabeticalFilteredMap = new HashMap<>();
        allWords.entrySet().stream().filter(k -> k.getKey().matches(regex))
                .forEach(k -> alphabeticalFilteredMap.put(k.getKey(), k.getValue()));
        writeToFile(fileName, alphabeticalFilteredMap);
    }

    private void writeToFile(String fileName, Map<String, Integer> sortedMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
