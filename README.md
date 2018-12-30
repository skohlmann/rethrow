# rethrow

A simple example for transforming checked exceptions into unchecked exceptions for simple use in Java Streams.

## Usage

### Before
    
    import java.io.IOException;
    import java.io.UncheckedIOException;
    
    public class PathContainer implements Iterable<Path> {
    
        public void copyAllTo(final Path target) {
            forEach(source -> {
                try {
                    Files.copy(source, target);
                } catch (final IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
    }

### After

    import static de.speexx.rethrow.ThrowingConsumer.rethrowUnchecked;

    public class PathContainer implements Iterable<Path> {
    
        public void copyAllTo(final Path target) {
            forEach(rethrowUnchecked(source -> Files.copy(source, target)));
        }
    }

That's all, folks. 

Savings:
- ~25% code & statements
- 6 lines of code & statements
