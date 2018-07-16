package nurisezgin.com.android.statekeeper.storage;

import java.io.File;

/**
 * Created by nuri on 16.07.2018
 */
public final class StorageAdapterFactory {

    public static StorageAdapter newStorageAdapter(File dir, String name) {
        return new StorageAdapterImp(new FileStreamAdapterImp(dir, name));
    }

}
