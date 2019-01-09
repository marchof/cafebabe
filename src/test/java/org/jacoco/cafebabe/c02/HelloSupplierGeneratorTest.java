package org.jacoco.cafebabe.c02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Supplier;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

public class HelloSupplierGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	public void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	public void get_should_return_hello() throws Exception {
		cl.add(HelloSupplierGenerator.create());

		Supplier<Object> supplier = cl.newInstance("HelloSupplier");

		assertEquals("Hello", supplier.get());
	}

	@Test
	public void generator_should_use_event_APIs_correctly() {
		HelloSupplierGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
