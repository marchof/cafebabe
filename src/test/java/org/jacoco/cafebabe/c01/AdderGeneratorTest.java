package org.jacoco.cafebabe.c01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

public class AdderGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	public void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	public void add_should_add_two_integers() throws Exception {
		cl.add(AdderGenerator.create());

		Class<?> clazz = cl.loadClass("Adder");
		int result = (int) clazz.getMethod("add", Integer.TYPE, Integer.TYPE).invoke(null, 13, 29);

		assertEquals(42, result);
	}

	@Test
	public void generator_should_use_event_APIs_correctly() {
		AdderGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
