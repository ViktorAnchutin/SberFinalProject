package com.vanchutin.simulation;

@FunctionalInterface
public interface ThreadCallback<T> {
    void execute(T t);
}
