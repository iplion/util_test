public class Util {
    public static void main(String[] args) {
        AppData appData = new AppData(args);
        if (appData.isNeedHelp()) {
            Talk.sayHelp();

            return;
        }
        if (!appData.getFatalInitError().isEmpty()) {
            Talk.sayError(appData.getFatalInitError());

            return;
        }

        Queues queues = new Queues(
                appData.getResultFilesPath(),
                appData.getIntFilename(),
                appData.getRealFilename(),
                appData.getStringFilename(),
                appData.isAppendResultFiles()
        );

        FileReader.start(appData.getFilesToProcessing(), queues);

        queues.stopProcessing();

        Talk.sayStatistics(appData.isDisplayFullStatistics());
    }
}