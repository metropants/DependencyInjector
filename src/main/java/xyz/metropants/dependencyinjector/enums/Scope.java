package xyz.metropants.dependencyinjector.enums;

public enum Scope {

    /**
     * A bean with this scope will be instantiated once per application context.
     */
    SINGLETON,

    /**
     * A bean with this scope will be instantiated once per request.
     */
    PROTOTYPE

}
