package com.vanchutin.simulation.old;

@FunctionalInterface
public interface ThreadCallback<T> {
    void execute(T t);
}
