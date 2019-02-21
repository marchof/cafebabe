package org.jacoco.cafebabe.c20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntBinaryOperator;

import org.jacoco.cafebabe.c20.MaxGenerator;
import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class MaxGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void add_should_add_two_integers() throws Exception {
		cl.add(MaxGenerator.create());

		IntBinaryOperator max = cl.newInstance("Max");

		assertEquals(55, max.applyAsInt(-3, 55));

		assertEquals(19, max.applyAsInt(19, 18));
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		MaxGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
