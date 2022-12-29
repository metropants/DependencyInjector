package xyz.metropants.dependencyinjector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.metropants.dependencyinjector.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InjectorTest {

    static ApplicationContext ctx;

    @BeforeAll
    static void setup() {
        DependencyInjector.run(InjectorTest.class);
        ctx = DependencyInjector.getContext();
    }

    @Test
    void testApplicationContext() {
        assertNotNull(ctx);
    }

}
