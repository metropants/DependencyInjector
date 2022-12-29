package xyz.metropants.dependencyinjector;

import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.metropants.dependencyinjector.annotation.Bean;
import xyz.metropants.dependencyinjector.bean.BeanMetadata;
import xyz.metropants.dependencyinjector.enums.Scope;
import xyz.metropants.dependencyinjector.util.ConstructorUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Injector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Injector.class);

    private final Map<String, BeanMetadata> beans = new ConcurrentHashMap<>();
    private final Reflections reflections;

    private static Injector instance;

    private Injector(@NotNull Class<?> mainClass) {
        reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage(mainClass.getPackageName())
                        .addScanners(Scanners.TypesAnnotated)
        );

        generateBeans();
    }

    private void addBean(@NotNull BeanMetadata metadata) {
        beans.put(metadata.getName(), metadata);
    }

    private String resolveBeanNames(@NotNull Class<?> beanClass) {
        final Bean bean = beanClass.getAnnotation(Bean.class);
        return !bean.name().isEmpty() ? bean.name() : beanClass.getSimpleName();
    }

    private BeanMetadata createBean(@NotNull Class<?> beanClass) {
        final Bean bean = beanClass.getAnnotation(Bean.class);
        final Scope scope = bean.scope();
        final String name = resolveBeanNames(beanClass);

        if (beanClass.isInterface() || Modifier.isAbstract(beanClass.getModifiers())) {
            throw new IllegalArgumentException("Cannot create " + name +  " bean from interface or abstract class.");
        }

        final Constructor<?> constructor = ConstructorUtils.findMostParameterizedConstructor(beanClass);
        Object[] args = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            final Parameter parameter = constructor.getParameters()[i];
            final Class<?> type = parameter.getType();

            final String parameterName = resolveBeanNames(type);
            if (parameterName == null) {
                throw new IllegalArgumentException("Cannot find bean for " + type.getName() + ".");
            }

            Object arg = getBean(parameterName);
            if (arg == null) {
                throw new IllegalArgumentException("Cannot find bean for " + type.getName() + ".");
            }

            args[i] = arg;
        }

        try {
            Object instance = constructor.newInstance(args);
            return BeanMetadata.builder()
                    .name(name)
                    .type(beanClass)
                    .scope(scope)
                    .instance(instance)
                    .build();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Cannot create bean for " + name + ".", e);
        }
    }

    private void generateBeans() {
        final Set<Class<?>> beanClasses = reflections.getTypesAnnotatedWith(Bean.class);
        for (Class<?> beanClass : beanClasses) {
            if (beanClass == null || !beanClass.isAnnotationPresent(Bean.class)) {
                continue;
            }

            BeanMetadata metadata = createBean(beanClass);
            addBean(metadata);
            LOGGER.info("Registered bean of {}.", metadata.getName());
        }
    }

    public static Injector initialize(@NotNull Class<?> mainClass) {
        synchronized (Injector.class) {
            if (instance == null) {
                instance = new Injector(mainClass);
            }
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(@NotNull String name) {
        BeanMetadata metadata = beans.get(name);
        if (metadata == null) {
            return null;
        }

        if (!metadata.isSingleton()) {
            BeanMetadata newMetadata = createBean(metadata.getType());
            addBean(newMetadata);

            return (T) newMetadata.getInstance();
        }

        return (T) metadata.getInstance();
    }

    public String[] getBeanNames() {
        return beans.keySet().toArray(new String[0]);
    }

    public Map<String, BeanMetadata> getBeans() {
        return beans;
    }

}
