package nurisezgin.com.android.statekeeper.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nurisezgin.com.android.statekeeper.annotations.ColdState;
import nurisezgin.com.android.statekeeper.annotations.HotState;
import nurisezgin.com.android.statekeeper.annotations.StateIdentifier;

/**
 * Created by nuri on 20.07.2018
 */
public class DeliveryContext {

    @HotState
    public boolean booleanValue;

    @HotState
    public int intValue;

    @HotState
    public float floatValue;

    @HotState
    public String stringValue;

    @HotState
    public SerializableClass serializableValue;

    @HotState
    public ParcelableClass parcelableValue;

    @StateIdentifier
    @HotState
    public String instanceId;

    @ColdState
    public List<ListItem> assets;

    public DeliveryContext() {
        instanceId = UUID.randomUUID().toString();
        assets = new ArrayList<>();
    }


}
