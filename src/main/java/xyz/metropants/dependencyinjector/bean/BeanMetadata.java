package xyz.metropants.dependencyinjector.bean;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.metropants.dependencyinjector.enums.Scope;

import java.util.List;

public class BeanMetadata {

    private final String name;
    private final Scope scope;
    private final Class<?> type;
    private Object instance;

    public BeanMetadata(String name, Scope scope, Class<?> type, Object instance) {
        this.name = name;
        this.scope = scope;
        this.type = type;
        this.instance = instance;
    }

    public BeanMetadata(String name, Scope scope, Class<?> type) {
        this(name, scope, type, null);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull BeanMetadataBuilder builder() {
        return new BeanMetadataBuilder();
    }

    public boolean isSingleton() {
        return scope == Scope.SINGLETON;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public Scope getScope() {
        return scope;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getInstance() {
        return instance;
    }

}
