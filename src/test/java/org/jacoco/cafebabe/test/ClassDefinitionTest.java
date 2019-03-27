package org.jacoco.cafebabe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;

public class ClassDefinitionTest {

	@Test
	void should_load_top_level_class() throws Exception {
		byte[] definition = ClassDefinition.get(ClassDefinitionTest.class);
		assertNotNull(definition);

		var reader = new ClassReader(definition);
		assertEquals("org/jacoco/cafebabe/test/ClassDefinitionTest", reader.getClassName());
	}

	class Inner {
	}

	@Test
	void should_load_inner_class() throws Exception {
		byte[] definition = ClassDefinition.get(Inner.class);
		assertNotNull(definition);

		var reader = new ClassReader(definition);
		assertEquals("org/jacoco/cafebabe/test/ClassDefinitionTest$Inner", reader.getClassName());
	}

}
