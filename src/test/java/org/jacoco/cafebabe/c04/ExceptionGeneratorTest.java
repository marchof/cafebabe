package org.jacoco.cafebabe.c04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link ExceptionGenerator}.
 */
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

		Exception ex = assertThrows(Exception.class, runnable::run);

		StackTraceElement top = ex.getStackTrace()[0];
		assertEquals("ExceptionRunnable", top.getClassName());
		assertEquals("run", top.getMethodName());
		assertEquals("AnyNameYouLike.c", top.getFileName());
		assertEquals(12345, top.getLineNumber());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		ExceptionGenerator.create(new CheckClassAdapter(null));
	}

}
