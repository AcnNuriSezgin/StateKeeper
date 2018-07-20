package nurisezgin.com.android.statekeeper.storage;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Supplier;

/**
 * Created by nuri on 16.07.2018
 */
public interface StorageAdapter {

    void write(Supplier<Object> objectSupplier);

    void read(Consumer<Object> readAction);

    void delete();
}
