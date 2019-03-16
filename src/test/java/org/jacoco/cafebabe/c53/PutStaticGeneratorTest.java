package org.jacoco.cafebabe.c53;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.objectweb.asm.Opcodes.V1_8;
import static org.objectweb.asm.Opcodes.V9;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

class PutStaticGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void run_should_change_value_of_static_final_when_java_6_bytecode() throws Exception {
		cl.add(PutStaticGenerator.create(V1_8));
		Class<?> clazz = cl.loadClass("Example");

		assertEquals(42, clazz.getField("CONST").get(null));

		((Runnable) clazz.getConstructor().newInstance()).run();
		assertEquals(13, clazz.getField("CONST").get(null));
	}

	@Test
	void run_should_not_change_value_of_static_final_when_java_9_bytecode() throws Exception {
		cl.add(PutStaticGenerator.create(V9));
		Runnable runnable = cl.newInstance("Example");

		IllegalAccessError thrown = assertThrows(IllegalAccessError.class, runnable::run);
		assertEquals("Update to static final field Example.CONST attempted from a different method (run) than the initializer method <clinit> ", thrown.getMessage());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		PutStaticGenerator.create(V1_8, new CheckClassAdapter(null));
	}

}
