package nurisezgin.com.android.statekeeper.storage;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import static nurisezgin.com.android.statekeeper.util.StreamUtil.closeStreams;

/**
 * Created by nuri on 16.07.2018
 */
final class StorageAdapterImp implements StorageAdapter {

    private FileStreamAdapter fileStreamAdapter;

    StorageAdapterImp(FileStreamAdapter fileStreamAdapter) {
        this.fileStreamAdapter = fileStreamAdapter;
    }

    @Override
    public void write(Supplier<Object> objectSupplier) {
        OutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = fileStreamAdapter.writer();
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(objectSupplier.get());
            outputStream.write(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(outputStream, byteArrayOutputStream, objectOutputStream);
        }
    }

    @Override
    public void read(Consumer<Object> readAction) {
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputStream = fileStreamAdapter.reader();
            dataInputStream = new DataInputStream(inputStream);

            byte[] bytes = new byte[fileStreamAdapter.length()];
            dataInputStream.readFully(bytes);

            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);

            readAction.accept(objectInputStream.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputStream, dataInputStream, byteArrayInputStream, objectInputStream);
        }
    }

    @Override
    public void delete() {
        fileStreamAdapter.delete();
    }
}
