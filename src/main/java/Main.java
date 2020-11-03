import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * TODO:
 *  parsing date with pattern
 */

public class Main {
    public static void main(String[] args) {
        try {
            final String TEST_KEY = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String SOME_KEY = "695Aee8A191cbc011c16745bdbA36822";
            String dateFormat = "YYYY-m-d H-m-s";
            LocalDate l = LocalDate.parse("2020-11-3 18-08-6", DateTimeFormatter.ofPattern(dateFormat));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
