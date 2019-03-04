package org.jacoco.cafebabe.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

class AdderGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void add_should_add_two_integers() throws Exception {
		cl.add(AdderGenerator.create());

		Class<?> clazz = cl.loadClass("Adder");
		int result = (int) clazz.getMethod("add", Integer.TYPE, Integer.TYPE).invoke(null, 13, 29);

		assertEquals(42, result);
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		AdderGenerator.create(new CheckClassAdapter(null));
	}

}
