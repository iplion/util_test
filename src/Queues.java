import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;

class Queues {
    private final Queue<String> intNumQ = new ArrayDeque<>();
    private final Queue<String> realNumQ = new ArrayDeque<>();
    private final Queue<String> stringQ = new ArrayDeque<>();
    private boolean isAllDataProcessed = false;
    private final DataWriter intWriter;
    private final DataWriter realWriter;
    private final DataWriter stringWriter;
    Queues(
            String resultFilesPath,
            String intFilename,
            String realFilename,
            String stringFilename,
            boolean isAppendIntoFiles
    ) {
        DataWriter.queues = this;
        DataWriter.isAppendIntoFile = isAppendIntoFiles;
        intWriter = new DataWriter(
                Path.of(resultFilesPath, intFilename),
                intNumQ
        );
        realWriter = new DataWriter(
                Path.of(resultFilesPath, realFilename),
                realNumQ
        );
        stringWriter = new DataWriter(
                Path.of(resultFilesPath, stringFilename),
                stringQ
        );
    }
    public void putDataInto(String line) {
        Queue<String> selectedQ;
        Thread selectedThread;
        try {
            long longNum = Long.parseLong(line);
            selectedQ = intNumQ;
            selectedThread = intWriter.t;
            Statistics.addRecord(longNum);
        } catch (NumberFormatException e) {
            try {
                double doubleNum = Double.parseDouble(line);
                selectedQ = realNumQ;
                selectedThread = realWriter.t;
                Statistics.addRecord(doubleNum);
            } catch (NumberFormatException exception) {
                selectedQ = stringQ;
                selectedThread = stringWriter.t;
                Statistics.addRecord(line);
            }
        }

        synchronized (selectedQ) {
            selectedQ.offer(line);
            if (selectedThread.isAlive()) {
                selectedQ.notify();
            } else {
                selectedThread.start();
            }
        }
    }

    public String getDataFrom(Queue<String> q) {
        while (q.isEmpty() && !isAllDataProcessed) {
            synchronized (q) {
                try {
                    q.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return q.poll();
    }

    public void stopProcessing() {
        isAllDataProcessed = true;
        synchronized (intNumQ) {
            intNumQ.notify();
        }
        synchronized (realNumQ) {
            realNumQ.notify();
        }
        synchronized (stringQ) {
            stringQ.notify();
        }

        try {
            intWriter.t.join();
            realWriter.t.join();
            stringWriter.t.join();
        } catch (InterruptedException e) {
            Talk.sayError("The main thread just interrupted. I only need to sleep for two seconds to wait for worker threads. Sorry.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Talk.sayError("I couldn't sleep.");
            }
        }
    }

}


//    @FunctionalInterface
//    interface GetDataFromQueue<T> {
//        String getData(T q, String name);
//    }
