import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static void start(List<String> resourceFilenames, Queues q) {
        List<Scanner> resourceFiles = new ArrayList<>();
        for (String filePath : resourceFilenames) {
            try {
                Path path = Path.of(filePath);

                Scanner scanner = new Scanner(path);
                if (!scanner.hasNext()) {
                    throw new EmptyFileException(filePath);
                }

                resourceFiles.add(scanner);
            } catch (EmptyFileException e) {
                Talk.sayWarning("Oops! The file \"" + e.getMessage() + "\" is empty!");
            } catch (IOException e) {
                Talk.sayWarning("Sorry friend, but i couldn't read the file \"" + e.getMessage() + "\"");
            }
        }

        while (!resourceFiles.isEmpty()) {
            resourceFiles.forEach(scanner -> {
                String str = scanner.nextLine();
                q.putDataInto(str);
            });

            List<Scanner> processedFiles = resourceFiles.stream()
                    .filter(scanner -> !scanner.hasNext())
                    .toList();
            processedFiles.forEach(Scanner::close);
            resourceFiles.removeAll(processedFiles);
        }
    }
}
