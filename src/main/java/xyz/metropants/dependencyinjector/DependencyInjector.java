package xyz.metropants.dependencyinjector;

import org.jetbrains.annotations.NotNull;
import xyz.metropants.dependencyinjector.context.ApplicationContext;
import xyz.metropants.dependencyinjector.context.ApplicationContextImpl;

public final class DependencyInjector {

    private static ApplicationContext context;

    private DependencyInjector() {}

    public static void run(@NotNull Class<?> mainClass) {
        Injector injector = Injector.initialize(mainClass);
        if (injector == null) {
            throw new IllegalStateException("Failed to initialize injector.");
        }

        context = new ApplicationContextImpl(injector);
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
