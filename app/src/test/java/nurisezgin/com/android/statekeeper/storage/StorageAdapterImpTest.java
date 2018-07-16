package nurisezgin.com.android.statekeeper.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import nurisezgin.com.android.statekeeper.testutils.Car;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nuri on 16.07.2018
 */
@RunWith(PowerMockRunner.class)
public class StorageAdapterImpTest {

    @Mock
    FileStreamAdapter mockFileStreamAdapter;

    private StorageAdapter storageAdapter;

    @Before
    public void setUp_() {
        storageAdapter = new StorageAdapterImp(mockFileStreamAdapter);
    }

    @Test
    public void should_WriteCorrect() throws IOException {
        Car car = new Car("");

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockFileStreamAdapter.writer()).thenReturn(mockOutputStream);

        storageAdapter.write(() -> car);

        verify(mockOutputStream).write(any(byte[].class));
    }

    @Test
    public void should_ReadCorrect() throws InterruptedException, IOException {
        final String expectedModel = "John";

        Car car = new Car(expectedModel);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(car);
        final byte[] bytes = byteArrayOutputStream.toByteArray();

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt()))
                .then(invocation -> {
                    byte[] source = (byte[]) invocation.getArguments()[0];
                    int offset = (int) invocation.getArguments()[1];
                    int len = (int) invocation.getArguments()[2];

                    System.arraycopy(bytes, offset, source, 0, len);

                    return len;
                });

        when(mockFileStreamAdapter.reader()).thenReturn(mockInputStream);
        when(mockFileStreamAdapter.length()).thenReturn(bytes.length);

        BlockingQueue<Car> queue = new ArrayBlockingQueue<>(1);

        storageAdapter.read(o -> queue.offer((Car) o));

        Car actual = queue.poll(1, TimeUnit.SECONDS);
        String actualName = actual.model;

        assertThat(actualName, is(expectedModel));
    }

}