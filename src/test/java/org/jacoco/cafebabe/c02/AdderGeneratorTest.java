package org.jacoco.cafebabe.c02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntBinaryOperator;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link AdderGenerator}.
 */
class AdderGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void add_should_add_two_integers() throws Exception {
		cl.add(AdderGenerator.create());

		IntBinaryOperator adder = cl.newInstance("Adder");
		var result = adder.applyAsInt(13, 29);

		assertEquals(42, result);
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		AdderGenerator.create(new CheckClassAdapter(null));
	}

}
