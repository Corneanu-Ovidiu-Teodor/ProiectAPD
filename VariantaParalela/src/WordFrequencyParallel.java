import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class WordFrequencyParallel {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors(); 

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String filePath = "C:\\Users\\lupur\\IdeaProjects\\VariantaParalela\\src\\input";
        String outputFilePath = "C:\\Users\\lupur\\IdeaProjects\\VariantaParalela\\src\\output";

        long startTime = System.nanoTime();

        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        }

        int totalLines = allLines.size();
        int blockSize = (int) Math.ceil(totalLines * 1.0 / NUM_THREADS);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < totalLines; i += blockSize) {
            int end = Math.min(i + blockSize, totalLines);
            List<String> subList = allLines.subList(i, end);
            Callable<Map<String, Integer>> task = new WordCounterTask(subList);
            futures.add(executor.submit(task));
        }

        Map<String, Integer> finalResult = new HashMap<>();
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> localMap = future.get();
            for (Map.Entry<String, Integer> entry : localMap.entrySet()) {
                finalResult.put(entry.getKey(), finalResult.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }

        executor.shutdown();

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Frecvența cuvintelor în fișier (variantă paralelă):\n");
            for (Map.Entry<String, Integer> entry : finalResult.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
            writer.write("\nTimp de execuție: " + (executionTime / 1_000_000) + " milisecunde\n");
        }

        System.out.println("Rezultatele și timpul de execuție au fost salvate în 'output_results_parallel.txt'.");
    }
}
