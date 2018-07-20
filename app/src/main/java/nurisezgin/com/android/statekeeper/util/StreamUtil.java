package nurisezgin.com.android.statekeeper.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by nuri on 16.07.2018
 */
public class StreamUtil {

    public static void closeStreams(Closeable... args) {
        for (Closeable a: args) {
            if (a != null) {
                try {
                    a.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
