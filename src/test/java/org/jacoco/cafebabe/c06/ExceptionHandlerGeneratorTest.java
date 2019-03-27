package org.jacoco.cafebabe.c06;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link ExceptionHandlerGenerator}.
 */
class ExceptionHandlerGeneratorTest {

	private Predicate<Runnable> predicate;

	@BeforeEach
	void before() throws Exception {
		var classLoader = new MemoryClassLoader();
		classLoader.add(ExceptionHandlerGenerator.create());
		predicate = classLoader.newInstance("ExceptionHandler");
	}

	@Test
	void generated_class_should_return_true_when_Runnable_throws_Exception() {
		Runnable throwException = () -> {
			throw new RuntimeException();
		};
		assertTrue(predicate.test(throwException));
	}

	@Test
	void generated_class_should_return_false_when_no_Exception() {
		Runnable nop = () -> {
		};
		assertFalse(predicate.test(nop));
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		ExceptionHandlerGenerator.create(new CheckClassAdapter(null));
	}

}
