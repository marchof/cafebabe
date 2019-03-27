package org.jacoco.cafebabe.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link AdderGenerator}.
 */
class AdderGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void add_should_add_two_integers() throws Throwable {
		cl.add(AdderGenerator.create());

		Class<?> clazz = cl.loadClass("Adder");
		var result = (int) MethodHandles.lookup()
				.findStatic(clazz, "add", MethodType.methodType(int.class, int.class, int.class)) //
				.invoke(13, 29);

		assertEquals(42, result);
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		AdderGenerator.create(new CheckClassAdapter(null));
	}

}
