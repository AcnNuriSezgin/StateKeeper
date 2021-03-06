package nurisezgin.com.android.statekeeper.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nuri on 16.07.2018
 */
final class FileStreamAdapterImp implements FileStreamAdapter {

    private File file;

    FileStreamAdapterImp(File dir, String fileName) {
        this.file = new File(dir, fileName);
    }

    @Override
    public InputStream reader() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream writer() throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    @Override
    public int length() {
        if (file.exists()) {
            return (int) file.length();
        }

        return 0;
    }

    @Override
    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }
}
