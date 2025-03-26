import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {

    public static void main(String[] args) {
        String filePath = "D:\\Intelij\\untitled\\src\\input";
        String outputFilePath = "D:\\Intelij\\untitled\\src\\output_results.txt";
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        long startTime = System.nanoTime();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\W+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Frecvența cuvintelor în fișier:\n");
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            writer.write("\nTimp de execuție: " + (executionTime / 1_000_000) + " milisecunde\n");
        } catch (IOException e) {
            System.err.println("Eroare la scrierea în fișier: " + e.getMessage());
        }

        System.out.println("Rezultatele și timpul de execuție au fost salvate în 'output_results.txt'.");
    }
}
