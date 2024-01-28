public class Statistics {
    private static int recordsTotal = 0;
    private static long longMinValue;
    private static long longMaxValue;
    private static long longNumSum;
    private static int longNumCount = 0;
    private static double doubleMinValue;
    private static double doubleMaxValue;
    private static double doubleNumSum;
    private static int doubleNumCount = 0;
    private static int stringsCount = 0;
    private static int stringMinLength;
    private static int stringMaxLength;

    public static void addRecord(long num) {
        longNumSum = (longNumCount == 0) ? num : longNumSum + num;
        longMinValue = (longNumCount == 0) ? num : Math.min(num, longMinValue);
        longMaxValue = (longNumCount == 0) ? num : Math.max(num, longMaxValue);
        recordsTotal++;
        longNumCount++;
    }

    public static void addRecord(double num) {
        doubleNumSum = (doubleNumCount == 0) ? num : doubleNumSum + num;
        doubleMinValue = (doubleNumCount == 0) ? num : Math.min(num, doubleMinValue);
        doubleMaxValue = (doubleNumCount == 0) ? num : Math.max(num, doubleMaxValue);
        recordsTotal++;
        doubleNumCount++;
    }

    public static void addRecord(String str) {
        int strLength = str.length();
        if (stringsCount++ == 0) {
            stringMinLength = stringMaxLength = strLength;

            return;
        }
        stringMinLength = Math.min(strLength, stringMinLength);
        stringMaxLength = Math.max(strLength, stringMaxLength);
        recordsTotal++;
    }

    public static long getLongAverage() throws ArithmeticException {
        return longNumSum / longNumCount;
    }

    public static double getDoubleAverage() throws ArithmeticException {
        return doubleNumSum / doubleNumCount;
    }

    public static int getRecordsTotal() {
        return recordsTotal;
    }

    public static long getLongMinValue() {
        return longMinValue;
    }

    public static long getLongMaxValue() {
        return longMaxValue;
    }

    public static long getLongNumSum() {
        return longNumSum;
    }

    public static int getLongNumCount() {
        return longNumCount;
    }

    public static double getDoubleMinValue() {
        return doubleMinValue;
    }

    public static double getDoubleMaxValue() {
        return doubleMaxValue;
    }

    public static double getDoubleNumSum() {
        return doubleNumSum;
    }

    public static int getDoubleNumCount() {
        return doubleNumCount;
    }

    public static int getStringsCount() {
        return stringsCount;
    }

    public static int getStringMinLength() {
        return stringMinLength;
    }

    public static int getStringMaxLength() {
        return stringMaxLength;
    }
}
