package org.jacoco.cafebabe.c51;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link StaticInitGenerator}.
 */
class StaticInitGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_have_constant_field() throws Exception {
		cl.add(StaticInitGenerator.create());
		Class<?> clazz = cl.loadClass("Constants");

		assertNotNull(clazz.getField("CONST").get(null));

	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		StaticInitGenerator.create(new CheckClassAdapter(null));
	}

}
