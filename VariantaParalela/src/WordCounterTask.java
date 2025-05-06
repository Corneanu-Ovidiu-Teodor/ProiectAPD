import java.util.*;
import java.util.concurrent.Callable;

public class WordCounterTask implements Callable<Map<String, Integer>> {
    private final List<String> lines;

    public WordCounterTask(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Map<String, Integer> call() {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String line : lines) {
            String[] words = line.toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        return wordCount;
    }
}
