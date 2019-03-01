package org.jacoco.cafebabe.c50;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class ConstantsGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_have_constant_fields() throws Exception {
		cl.add(ConstantsGenerator.create());
		Class<?> clazz = cl.loadClass("Constants");

		assertEquals(42, clazz.getField("CONST_INT").get(null));
		assertEquals(1234L, clazz.getField("CONST_LONG").get(null));
		assertEquals((short) 15, clazz.getField("CONST_SHORT").get(null));
		assertEquals((byte) 7, clazz.getField("CONST_BYTE").get(null));
		assertEquals('c', clazz.getField("CONST_CHAR").get(null));
		assertEquals(0.25f, clazz.getField("CONST_FLOAT").get(null));
		assertEquals(0.5, clazz.getField("CONST_DOUBLE").get(null));
		assertEquals(true, clazz.getField("CONST_BOOLEAN").get(null));
		assertEquals("cafebabe", clazz.getField("CONST_STRING").get(null));

	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		ConstantsGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
