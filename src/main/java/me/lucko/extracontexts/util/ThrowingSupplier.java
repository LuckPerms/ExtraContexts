package me.lucko.extracontexts.util;

import java.util.function.Supplier;

public interface ThrowingSupplier<T> extends Supplier<T> {

    static <T> ThrowingSupplier<T> of(ThrowingSupplier<T> supplier) {
        return supplier;
    }

    @Override
    default T get() {
        try {
            return getExceptionally();
        } catch (Exception e) {
            throw new RuntimeException("Unable to supply", e);
        }
    }

    T getExceptionally() throws Exception;

    default ThrowingSupplier<T> or(ThrowingSupplier<T> other) {
        ThrowingSupplier<T> thisSupplier = this;
        return () -> {
            try {
                return thisSupplier.getExceptionally();
            } catch (Exception e) {
                return other.getExceptionally();
            }
        };
    }
}
