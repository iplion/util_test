import java.util.List;

public class Talk {
    static void say(String str) {
        System.out.println(str);
    }

    static void sayError(String str) {
        System.out.printf("ERROR: %s\n", str);
    }

    static void sayWarning(String str) {
        System.out.printf("Warning: %s\n", str);
    }

    static boolean ask(String question, boolean defaultAnswer) {
        final List<String> yes = List.of("yes", "y");
        final List<String> no = List.of("no", "n");
        String ask = defaultAnswer ? "%s [Y/n]\n" : "%s [y/N]\n";

        while (true) {
            System.out.printf(ask, question);
            String userAnswer = System.console().readLine().toLowerCase();

            if (yes.contains(userAnswer)) {
                return true;
            }
            if (no.contains(userAnswer)) {
                return false;
            }
            if (userAnswer.isEmpty()) {
                return defaultAnswer;
            }
            System.out.println("I'm so sorry, I did not understand you.");
        }
    }

    static void sayStatistics(boolean isPrintFullStatistics) {
        Talk.say("-----------------------------------------");
        Talk.say("Total records processed: " + Statistics.getRecordsTotal());
        Talk.say("-----------------------------------------");
        if (!isPrintFullStatistics) {
            return;
        }

        if (Statistics.getLongNumCount() == 0) {
            Talk.say("There were no any integer values");
        } else {
            Talk.say("Maximum integer : " + Statistics.getLongMaxValue());
            Talk.say("Minimum integer : " + Statistics.getLongMinValue());
            Talk.say("Sum of integers : " + Statistics.getLongNumSum());
            Talk.say("Average integer : " + Statistics.getLongAverage());
        }
        Talk.say("-----------------------------------------");

        if (Statistics.getDoubleNumCount() == 0) {
            Talk.say("There were no any real values");
        } else {
            Talk.say("Maximum real : " + Statistics.getDoubleMaxValue());
            Talk.say("Minimum real : " + Statistics.getDoubleMinValue());
            Talk.say("Sum of reals : " + Statistics.getDoubleNumSum());
            Talk.say("Average real : " + Statistics.getDoubleAverage());
        }
        Talk.say("-----------------------------------------");

        if (Statistics.getStringsCount() == 0) {
            Talk.say("There were no any strings");
        } else {
            Talk.say("Number of strings : " + Statistics.getStringsCount());
            Talk.say("Maximum string length : " + Statistics.getStringMaxLength());
            Talk.say("Minimum string length : " + Statistics.getStringMinLength());
        }
        Talk.say("-----------------------------------------");
    }

    static void sayHelp() {
        System.out.println("""
                    Usage:
                    java -jar util.jar [options] <inFile 1> [inFile 2] ... [inFile N]
                        Possible [options]:
                            -s  display short statistics (default)
                            -f  display full statistics
                            -a  append result files (overwrite by default)
                            -o  result files path (current directory by default)
                            -p  result filenames prefix (none by default)
                            -h  --help  this message
                """);
    }
}
