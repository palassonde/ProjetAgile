
package projetsessioninf2015;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
/**
 *
 * @author achille
 */
class FileReader {
    
    public static String loadFileIntoString (String filePath, String fileEncoding) throws FileNotFoundException, IOException {
        return IOUtils.toString(new FileInputStream(filePath), fileEncoding);
    }
}
