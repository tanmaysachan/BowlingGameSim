import java.io.FileNotFoundException;
import java.io.IOException;

public interface BowlerFileHandler {
    /**
     * Retrieves a matching Bowler from the bowler database.
     *
     * @param nickName	The NickName of the Bowler
     *
     * @return a Bowler object.
     *
     */

    default Bowler registerPatron(String nickName) {
        Bowler patron = null;

        try {
            // only one patron / nick.... no dupes, no checks

            patron = BowlerFile.getBowlerInfo(nickName);

        } catch (FileNotFoundException e) {
            System.err.println("Error..." + e);
        } catch (IOException e) {
            System.err.println("Error..." + e);
        }

        return patron;
    }
}
