package org.jacoco.cafebabe.c52;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.function.Supplier;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link CondyGenerator}.
 */
class CondyGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_return_dynamically_created_constant() throws Exception {
		cl.add(CondyGenerator.create());

		Supplier<Object> supplier = cl.newInstance("Condy");

		assertEquals(LocalDate.now(), supplier.get());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		CondyGenerator.create(new CheckClassAdapter(null));
	}

}
