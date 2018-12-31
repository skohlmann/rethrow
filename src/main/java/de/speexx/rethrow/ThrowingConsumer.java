package de.speexx.rethrow;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import static java.util.Objects.requireNonNull;

/**
 * Exception throwing replacement of {@link Consumer}.
 * {@link #rethrowUnchecked(io.misterspex.image.fetcher.ThrowingConsumer) supports
 * rethrowing of an unchecked exception for a checked exception.
 * @author Sascha Kohlmann
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

    /**
     * Creates a consumer which transforms all checked exceptions to unchecked expceptions.
     * In standard cases a {@link RuntimeException} is thrown if and only if
     * the thrown exception is not of type <tt>RuntimeExceptiony</tt>.
     * <p>If and only if the thrown exception is of type {@link IOException} the
     * implementation throws an {@link UncheckedIOException}.</p>
     * @param <T> type to consume
     * @param <E> exception to be thrown
     * @param c an exception throwing consumer
     * @return a consumer
     */
    static <T, E extends Exception> Consumer<T> rethrowUnchecked(final ThrowingConsumer<T, E> c) {
        requireNonNull(c);
        return t -> {
            try {
                c.accept(t);
            } catch (final Exception e) {
                if (e instanceof RuntimeException) {throw (RuntimeException) e;}
                if (e instanceof IOException) {throw new UncheckedIOException((IOException) e);}
                throw new RuntimeException(e);
            }
        };
    }
}
