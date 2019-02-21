package org.jacoco.cafebabe.c04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class ExceptionGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void run_should_throw_exception_with_source_reference() throws Exception {
		cl.add(ExceptionGenerator.create());

		Runnable runnable = cl.newInstance("ExceptionRunnable");

		RuntimeException ex = assertThrows(RuntimeException.class, runnable::run);

		StackTraceElement top = ex.getStackTrace()[0];
		assertEquals("AnyNameYouLike.c", top.getFileName());
		assertEquals("run", top.getMethodName());
		assertEquals(12345, top.getLineNumber());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		ExceptionGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
