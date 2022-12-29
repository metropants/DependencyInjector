package xyz.metropants.dependencyinjector.context;

import org.jetbrains.annotations.NotNull;
import xyz.metropants.dependencyinjector.Injector;
import xyz.metropants.dependencyinjector.bean.BeanMetadata;

import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {

    private final Injector injector;

    public ApplicationContextImpl(@NotNull Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T> T getBean(@NotNull String name) {
        return injector.getBean(name);
    }

    @Override
    public <T> T getBean(@NotNull Class<T> type) {
        final String name = type.getSimpleName();
        return injector.getBean(name);
    }

    @Override
    public String[] getBeanNames() {
        return injector.getBeanNames();
    }

    @Override
    public Map<String, BeanMetadata> getBeans() {
        return injector.getBeans();
    }

}
