import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Queue;

public class DataWriter implements Runnable {
    static Queues queues;
    static boolean isAppendIntoFile = false; // -a command line argument
    Thread t = new Thread(this);
    private final Path path;
    private final Queue<String> queue;

    DataWriter(Path pathToResultFile, Queue<String> q) {
        this.path = pathToResultFile;
        this.queue = q;
    }

    @Override
    public void run() {
        BufferedWriter file = null;
        String str;
        while ((str = queues.getDataFrom(queue)) != null) {
            if (file == null) {
                if (!isAppendIntoFile && Files.exists(path)) {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        Talk.sayError("The file \"" + path.toString() + "\" clearing error.");
                    }
                }
                StandardOpenOption[] openParam = {
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                };
                try {
                    file = Files.newBufferedWriter(path, openParam);
                    file.write(str);
                    file.newLine();
                } catch (IOException e) {
                    Talk.sayError("The file \"" + path.toString() + "\" io error.");
                }
            } else {
                try {
                    file.write(str);
                    file.newLine();
                } catch (IOException e) {
                    Talk.sayError("The file \"" + path.toString() + "\" writing error.");
                }
            }
        }
        try {
            if (file != null) {
                file.close();
            }
        } catch (IOException e) {
            Talk.sayError("The file \"" + path.toString() + "\" closing error.");
        }
    }
}
