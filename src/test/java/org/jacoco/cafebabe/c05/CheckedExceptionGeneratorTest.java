package org.jacoco.cafebabe.c05;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

public class CheckedExceptionGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	public void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	public void run_should_throw_checked_exception() throws Exception {
		cl.add(CheckedExceptionGenerator.create());

		Runnable runnable = cl.newInstance("CheckedExceptionRunnable");

		assertThrows(IOException.class, runnable::run);
	}

	@Test
	public void generator_should_use_event_APIs_correctly() {
		CheckedExceptionGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
