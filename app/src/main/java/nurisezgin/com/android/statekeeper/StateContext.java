package nurisezgin.com.android.statekeeper;

import android.os.Bundle;

import java.io.File;

import lombok.Builder;
import lombok.Getter;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapter;
import nurisezgin.com.android.statekeeper.reflection.ReflectionAdapterFactory;

/**
 * Created by nuri on 20.07.2018
 */
@Builder
public class StateContext {

    @Getter
    private Bundle bundle;
    private Object thiz;
    @Getter
    private File cachePath;

    public ReflectionAdapter getReflectionAdapter() {
        return ReflectionAdapterFactory.newReflectionAdapter(thiz);
    }
}
