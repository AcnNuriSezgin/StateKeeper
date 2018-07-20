package nurisezgin.com.android.statekeeper;

import android.os.Bundle;

import com.annimon.stream.Stream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuri on 15.07.2018
 */
public final class StateKeeper implements StateChain {

    private List<StateChain> chains = new ArrayList<>();

    StateKeeper() {
        addStateHandler(new HotStateChain());
        addStateHandler(new ColdStateChain());
    }

    public static void save(File cachePath, Bundle bundle, Object thiz) {
        final StateContext stateContext = StateContext.builder()
                .cachePath(cachePath)
                .bundle(bundle)
                .thiz(thiz)
                .build();

        new StateKeeper().save(stateContext);
    }

    @Override
    public void save(StateContext stateContext) {
        Stream.of(chains)
                .forEach(adapter -> adapter.save(stateContext));
    }

    public static void restore(File cachePath, Bundle bundle, Object thiz) {
        final StateContext stateContext = StateContext.builder()
                .cachePath(cachePath)
                .bundle(bundle)
                .thiz(thiz)
                .build();

        new StateKeeper().restore(stateContext);
    }

    @Override
    public void restore(StateContext stateContext) {
        Stream.of(chains)
                .forEach(adapter -> adapter.restore(stateContext));
    }

    public void addStateHandler(StateChain adapter) {
        chains.add(adapter);
    }

    public void removeStateHandler(StateChain adapter) {
        chains.remove(adapter);
    }
}
