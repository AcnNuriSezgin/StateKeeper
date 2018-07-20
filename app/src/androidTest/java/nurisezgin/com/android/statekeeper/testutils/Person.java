package nurisezgin.com.android.statekeeper.testutils;

import java.io.Serializable;
import java.util.List;

import nurisezgin.com.android.statekeeper.annotations.HotState;

/**
 * Created by nuri on 20.07.2018
 */
public class Person implements Serializable {

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

    @HotState
    public List<ListItem> listValue;

}
