package org.jacoco.cafebabe.c12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

class InvokeInterfaceGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_invoke_supplier() throws Exception {
		cl.add(InvokeInterfaceGenerator.create());

		Function<Object, Object> function = cl.newInstance("InvokeInterface");
		Supplier<Object> supplier = () -> "Hello";
		assertEquals("Hello", function.apply(supplier));
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		InvokeInterfaceGenerator.create(new CheckClassAdapter(null));
	}

}
