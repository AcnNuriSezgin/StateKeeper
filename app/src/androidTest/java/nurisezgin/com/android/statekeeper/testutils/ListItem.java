package nurisezgin.com.android.statekeeper.testutils;

import java.io.Serializable;

/**
 * Created by nuri on 20.07.2018
 */
public class ListItem implements Serializable {

    public String value;

    public ListItem(String value) {
        this.value = value;
    }
}
