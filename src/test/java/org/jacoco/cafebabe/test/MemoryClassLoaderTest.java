package org.jacoco.cafebabe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.V11;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassWriter;

class MemoryClassLoaderTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void should_load_added_class() throws Exception {
		var cw = new ClassWriter(0);
		cw.visit(V11, ACC_PUBLIC, "Foo", null, "java/lang/Object", null);

		cl.add(cw.toByteArray());
		Class<?> clazz = cl.loadClass("Foo");

		assertEquals("Foo", clazz.getName());
	}

	@Test
	void should_load_class_from_parent_loader() throws Exception {
		Class<?> clazz = cl.loadClass("java.lang.String");

		assertEquals("java.lang.String", clazz.getName());
	}

	@Test
	void should_create_instance() throws Exception {
		Object instance = cl.newInstance("java.util.ArrayList");

		assertEquals(ArrayList.class, instance.getClass());
	}

	@Test
	void should_throw_exception_when_class_does_not_exist() throws Exception {
		assertThrows(ClassNotFoundException.class, () -> cl.loadClass("does.not.Exist"));
	}

}
