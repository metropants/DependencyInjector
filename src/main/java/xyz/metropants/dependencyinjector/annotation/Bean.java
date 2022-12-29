package xyz.metropants.dependencyinjector.annotation;

import xyz.metropants.dependencyinjector.enums.Scope;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Bean {

    /**
     * @return The name given to the bean. If not specified, the name will be the class name.
     */
    String name() default "";

    /**
     * @return The scope of the bean. If not specified, the scope will be {@link Scope#SINGLETON}.
     */
    Scope scope() default Scope.SINGLETON;

}
