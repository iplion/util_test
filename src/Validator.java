import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    static final String regexp;

    static {
        //ms win OS
        final String WIN_FILESYSTEM_REGEXP = "^[^/:\\*\\?\"<>|]*$";
        //MacOS
        final String MAC_FILESYSTEM_REGEXP = "^[^.][\\w-.\\s~`!@#$%\\^&()+={}\\[\\];',]*$";
        //unix like OS
        final String UNIX_FILESYSTEM_REGEXP = "^.+$";

        String os = System.getProperty("os.name").toLowerCase();
        regexp = os.contains("win")
                ? WIN_FILESYSTEM_REGEXP
                : os.contains("mac")
                ? MAC_FILESYSTEM_REGEXP
                : UNIX_FILESYSTEM_REGEXP;
    }

    public static void validate(String validationString) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(validationString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(validationString);
        }
    }
}
