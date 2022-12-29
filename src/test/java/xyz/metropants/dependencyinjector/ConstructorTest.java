package xyz.metropants.dependencyinjector;

import org.junit.jupiter.api.Test;
import xyz.metropants.dependencyinjector.util.ConstructorUtils;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ConstructorTest {

    @Test
    void testMostParameterizedConstructor() {
        Constructor<?> constructor = ConstructorUtils.findMostParameterizedConstructor(TestConstructorClass.class);
        int parameters = constructor.getParameterCount();

        assertNotEquals(1, parameters);
        assertEquals(2, parameters);
    }

    private static class TestConstructorClass {

        public TestConstructorClass(String string, int integer) {}

        public TestConstructorClass(String string) {
            this(string, 0);
        }

        public TestConstructorClass(int integer) {
            this(null, integer);
        }

        public TestConstructorClass() {
            this(null, 0);
        }

    }

}
