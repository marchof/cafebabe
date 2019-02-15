package org.jacoco.cafebabe.c14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.util.function.Supplier;

import org.jacoco.cafebabe.util.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class IndyGeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_use_mutable_call_site() throws Exception {
		cl.add(IndyGenerator.create());

		Supplier<Object> supplier = cl.newInstance("Indy");

		assertEquals("Hello", supplier.get());

		IndyGenerator.callSite.setTarget(MethodHandles.constant(String.class, "Indy!"));
		assertEquals("Indy!", supplier.get());
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		IndyGenerator.create(new CheckClassAdapter(new ClassWriter(0)));
	}

}
