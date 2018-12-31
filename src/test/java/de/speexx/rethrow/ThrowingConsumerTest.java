package de.speexx.rethrow;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.function.Executable;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThrowingConsumerTest {

    @Test
    public void rethrowRuntimeException() {
        final IllegalStateException toThrow = new IllegalStateException();
        
        try {
            createConsumer(toThrow).accept("");
        } catch (final Exception e) {
            assertSame(toThrow, e);
        }
    }

    @Test
    public void encapsulateInRuntimeException() {
        final InterruptedException toThrow = new InterruptedException();
        
        try {
            createConsumer(toThrow).accept("");
        } catch (final Exception e) {
            assertSame(RuntimeException.class, e.getClass());
            assertSame(toThrow, e.getCause());
        }
    }

    @Test
    public void encapsulateIOExceptionInUncheckedIOException() {
        final IOException toThrow = new IOException();
        
        try {
            createConsumer(toThrow).accept("");
        } catch (final Exception e) {
            assertSame(UncheckedIOException.class, e.getClass());
            assertSame(toThrow, e.getCause());
        }
    }
    
    @Test
    public void failFast() {
        final Executable failFast = () -> ThrowingConsumer.rethrowUnchecked(null);
        assertThrows(NullPointerException.class, failFast);
    }

    Consumer<String> createConsumer(final Exception toThrow) {
        return ThrowingConsumer.rethrowUnchecked((t) -> {throw toThrow;});
    }
}
