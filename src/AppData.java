import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class AppData {
    static final String INT_FILENAME = "integers.txt";
    static final String REAL_FILENAME = "floats.txt";
    static final String STRING_FILENAME = "strings.txt";
    private String fatalInitError = "";
    private boolean isNeedHelp = false;
    private boolean isDisplayFullStatistics = false; // -s (default) or -f command line argument
    private boolean isAppendResultFiles = false; // -a command line argument
    private String resultFilesPath = ""; // -o command line argument
    private String resultFilenamesPrefix = ""; // -p command line argument
    private final ArrayList<String> filesToProcessing = new ArrayList<>();

    AppData(String[] commandLineArguments) {
        if (commandLineArguments.length == 0) {
            isNeedHelp = true;

            return;
        }
        for (int i = 0; i < commandLineArguments.length; i++) {
            switch (commandLineArguments[i]) {
                case "-h":
                case "--help":
                    isNeedHelp = true;

                    return;
                case "-s":
                    isDisplayFullStatistics = false;
                    break;
                case "-f":
                    isDisplayFullStatistics = true;
                    break;
                case "-a":
                    isAppendResultFiles = true;
                    break;
                case "-o":
                    if (++i < commandLineArguments.length) {
                        try {
                            Validator.validate(commandLineArguments[i]);
                            Path path = Path.of(commandLineArguments[i]);

                            if (!Files.exists(path)) {
                                Talk.say("The path \"" + commandLineArguments[i] + "\" does not exist");

                                if (Talk.ask("Can I make that directory for you?", true)) {

                                    if ((new File(commandLineArguments[i])).mkdirs()) {
                                        resultFilesPath = commandLineArguments[i];
                                        Talk.say("Done!");
                                    } else {
                                        Talk.sayError("Couldn't make the directory.");
                                        throw new IllegalArgumentException(commandLineArguments[i]);
                                    }

                                } else {
                                    throw new IllegalArgumentException(commandLineArguments[i]);
                                }
                            }

                            if (!Files.isDirectory(path)) {
                                Talk.sayError(commandLineArguments[i] + " is not a directory!");
                                throw new IllegalArgumentException(commandLineArguments[i]);
                            }

                            resultFilesPath = path.toString();

                        } catch (IllegalArgumentException e) {
                            Talk.sayWarning("The path (-o) \"" + e.getMessage() + "\" is not valid");

                            if (Talk.ask("Do you want to continue in the current directory?", true)) {
                                resultFilesPath = "";
                            } else {
                                fatalInitError = "FATAL: There`s no way to continue.";

                                return;
                            }

                        }
                    } else {
                        Talk.sayWarning("There is no expected filepath (-o)");
                        i--;
                    }
                    break;
                case "-p":
                    if (++i < commandLineArguments.length) {
                        try {
                            Validator.validate(commandLineArguments[i]);
                            resultFilenamesPrefix = commandLineArguments[i];
                        } catch (IllegalArgumentException e) {
                            Talk.sayWarning("The filename prefix (-p) \"" + commandLineArguments[i] + "\" is not valid");
                            if (!Talk.ask("Are you sure you want to continue?", false)) {
                                fatalInitError = "Rejected by user.";

                                return;
                            }
                        }
                    } else {
                        Talk.sayWarning("There is no expected filename prefix (-p)");
                        i--;
                    }
                    break;
                default:
                    try {
                        Validator.validate(commandLineArguments[i]);
                        Path path = Path.of(commandLineArguments[i]);

                        if (Files.exists(path) && Files.isRegularFile(path)) {
                            filesToProcessing.add(commandLineArguments[i]);
                        } else {
                            Talk.say("The file \"" + commandLineArguments[i] + "\" does not exist");
                        }

                    } catch (IllegalArgumentException e) {
                        Talk.sayWarning("The filename \"" + e.getMessage() + "\" is not valid");
                    }
            }
        }

        if (filesToProcessing.isEmpty()) {
            fatalInitError = "There are no files to process!";
        }
    }

    public ArrayList<String> getFilesToProcessing() {
        return filesToProcessing;
    }

    public boolean isDisplayFullStatistics() {
        return isDisplayFullStatistics;
    }

    public boolean isAppendResultFiles() {
        return isAppendResultFiles;
    }

    public String getResultFilesPath() {
        return resultFilesPath;
    }

    public String getFatalInitError() {
        return fatalInitError;
    }

    public boolean isNeedHelp() {
        return isNeedHelp;
    }

    public String getIntFilename() {
        return resultFilenamesPrefix + INT_FILENAME;
    }

    public String getRealFilename() {
        return resultFilenamesPrefix + REAL_FILENAME;
    }

    public String getStringFilename() {
        return resultFilenamesPrefix + STRING_FILENAME;
    }
}
