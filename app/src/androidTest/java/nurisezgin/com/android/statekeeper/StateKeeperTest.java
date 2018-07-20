package nurisezgin.com.android.statekeeper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import nurisezgin.com.android.statekeeper.testutils.DeliveryContext;
import nurisezgin.com.android.statekeeper.testutils.ListItem;
import nurisezgin.com.android.statekeeper.testutils.ParcelableClass;
import nurisezgin.com.android.statekeeper.testutils.SerializableClass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nuri on 20.07.2018
 */
@RunWith(AndroidJUnit4.class)
public class StateKeeperTest {

    private Bundle bundle;
    private DeliveryContext folk;
    private Context ctx;

    @Before
    public void setUp_() {
        bundle = new Bundle();
        folk = new DeliveryContext();

        ctx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void should_SaveAndRestoreCorrect() {
        folk.booleanValue = true;
        folk.intValue = 101;
        folk.floatValue = 1 / 30000000f;
        folk.stringValue = "string";
        folk.serializableValue = new SerializableClass("serializable-class#" + 2);
        folk.parcelableValue = new ParcelableClass("parcelable-class#" + 4);

        for (int i = 0; i < 100; i++) {
            folk.assets.add(new ListItem("listItem-class#" + i));
        }

        StateKeeper.save(ctx.getCacheDir(), bundle, folk);

        folk.assets = null;

        StateKeeper.restore(ctx.getCacheDir(), bundle, folk);

        assertThat(folk, is(getMatcher()));
    }

    @NonNull
    private TypeSafeMatcher<DeliveryContext> getMatcher() {
        return new TypeSafeMatcher<DeliveryContext>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Person object is not expected state");
            }

            @Override
            protected boolean matchesSafely(DeliveryContext delivery) {
                return areAttributesOk(delivery) && delivery.assets.size() == 100;
            }

            private boolean areAttributesOk(DeliveryContext delivery) {
                return delivery.booleanValue != false
                        && delivery.intValue > 0
                        && delivery.floatValue != 0
                        && delivery.stringValue.equals("string")
                        && delivery.serializableValue.value.startsWith("serializable-class")
                        && delivery.parcelableValue.value.startsWith("parcelable-class");
            }

        };
    }

}