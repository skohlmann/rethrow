package de.speexx.rethrow;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;

public class ThrowingFunctionTest {
    

    @Test
    public void rethrowRuntimeException() {
        final IllegalStateException toThrow = new IllegalStateException();
        
        try {
            createFunction(toThrow).apply("");
        } catch (final Exception e) {
            assertSame(toThrow, e);
        }
    }

    @Test
    public void encapsulateInRuntimeException() {
        final InterruptedException toThrow = new InterruptedException();
        
        try {
            createFunction(toThrow).apply("");
        } catch (final Exception e) {
            assertSame(RuntimeException.class, e.getClass());
            assertSame(toThrow, e.getCause());
        }
    }

    @Test
    public void encapsulateIOExceptionInUncheckedIOException() {
        final IOException toThrow = new IOException();
        
        try {
            createFunction(toThrow).apply("");
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

    Function<String, String> createFunction(final Exception toThrow) {
        return ThrowingFunction.rethrowUnchecked((t) -> {throw toThrow;});
    }
}
