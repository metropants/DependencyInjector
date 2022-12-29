package xyz.metropants.dependencyinjector.bean;

import org.jetbrains.annotations.NotNull;
import xyz.metropants.dependencyinjector.enums.Scope;

public class BeanMetadataBuilder {

    private String name;
    private Scope scope;
    private Class<?> type;
    private Object instance;

    public BeanMetadataBuilder name(@NotNull String name) {
        this.name = name;
        return this;
    }

    public BeanMetadataBuilder scope(@NotNull Scope scope) {
        this.scope = scope;
        return this;
    }

    public BeanMetadataBuilder type(@NotNull Class<?> type) {
        this.type = type;
        return this;
    }

    public BeanMetadataBuilder instance(@NotNull Object instance) {
        this.instance = instance;
        return this;
    }

    private void validate() {
        if (this.name == null) {
            throw new IllegalStateException("Bean name is not set.");
        }

        if (this.scope == null) {
            throw new IllegalStateException("Bean scope is not set.");
        }

        if (this.type == null) {
            throw new IllegalStateException("Bean type is not set.");
        }
    }

    public BeanMetadata build() {
        validate();
        return new BeanMetadata(name, scope, type, instance);
    }

}
