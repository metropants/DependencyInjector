package xyz.metropants.dependencyinjector.context;

import org.jetbrains.annotations.NotNull;
import xyz.metropants.dependencyinjector.bean.BeanMetadata;

import java.util.Map;

public interface ApplicationContext {

    /**
     * @param name The name of the bean to retrieve.
     * @return An instance of the bean.
     */
    <T> T getBean(@NotNull String name);

    /**
     * @param type The type of the bean to retrieve.
     * @return An instance of the bean.
     */
    <T> T getBean(@NotNull Class<T> type);

    /**
     * @return The names of all beans in the application context.
     */
    String[] getBeanNames();

    /**
     * @return A map of all beans in the application context.
     */
    Map<String, BeanMetadata> getBeans();

}
