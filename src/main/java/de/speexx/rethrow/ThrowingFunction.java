package de.speexx.rethrow;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 * Exception throwing replacement of {@link Function}.
 * {@link #rethrowUnchecked(io.misterspex.image.fetcher.ThrowingFunction) supports
 * rethrowing of an unchecked exception for a checked exception.
 * @author Sascha Kohlmann
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T t) throws E;

    /**
     * Creates a function which transforms all checked exceptions to unchecked expceptions.
     * In standard cases a {@link RuntimeException} is thrown if and only if
     * the thrown exception is not of type <tt>RuntimeExceptiony</tt>.
     * <p>If and only if the thrown exception is of type {@link IOException} the
     * implementation throws an {@link UncheckedIOException}.</p>
     * @param <T> type to apply
     * @param <E> exception to be thrown
     * @param f an exception throwing function
     * @return a function
     */
    static <T, R, E extends Exception> Function<T, R> rethrowUnchecked(final ThrowingFunction<T, R, E> f) {
        requireNonNull(f);
        return t -> {
            try {
                return f.apply(t);
            } catch (final Exception e) {
                if (e instanceof RuntimeException) {throw (RuntimeException) e;}
                if (e instanceof IOException) {throw new UncheckedIOException((IOException) e);}
                throw new RuntimeException(e);
            }
        };
    }
}
