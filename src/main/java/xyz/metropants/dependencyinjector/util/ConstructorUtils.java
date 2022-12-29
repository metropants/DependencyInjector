package xyz.metropants.dependencyinjector.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public final class ConstructorUtils {

    private ConstructorUtils() {}

    /**
     * @param clazz The class to find the most parameterized constructor.
     * @return The most parameterized constructor.
     */
    public static Constructor<?> findMostParameterizedConstructor(@NotNull Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getConstructors();

        Constructor<?> mostParameterizedConstructor = constructors[0];
        for (Constructor<?> constructor : constructors) {
            if (constructor == null || mostParameterizedConstructor == null) {
                continue;
            }

            if (constructor.getParameterCount() > mostParameterizedConstructor.getParameterCount()) {
                mostParameterizedConstructor = constructor;
            }
        }
        return mostParameterizedConstructor;
    }

}
