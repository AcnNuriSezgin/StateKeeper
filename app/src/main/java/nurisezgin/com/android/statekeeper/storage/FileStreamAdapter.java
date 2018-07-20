package nurisezgin.com.android.statekeeper.storage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nuri on 16.07.2018
 */
interface FileStreamAdapter {

    InputStream reader() throws FileNotFoundException;

    OutputStream writer() throws FileNotFoundException;

    int length();

    void delete();

}
