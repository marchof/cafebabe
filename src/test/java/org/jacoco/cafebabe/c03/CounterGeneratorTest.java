package org.jacoco.cafebabe.c03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntSupplier;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class CounterGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_increment_value_after_every_call() throws Exception {
		cl.add(CounterGenerator.create());

		IntSupplier counter = cl.newInstance("Counter");

		assertEquals(1, counter.getAsInt());
		assertEquals(2, counter.getAsInt());
		assertEquals(3, counter.getAsInt());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		CounterGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
