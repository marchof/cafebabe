package org.jacoco.cafebabe.c51;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StaticInitGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void run_have_constant_fields() throws Exception {
		cl.add(StaticInitGenerator.create());
		Class<?> clazz = cl.loadClass("Constants");

		assertNotNull(clazz.getField("CONST").get(null));

	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		StaticInitGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
