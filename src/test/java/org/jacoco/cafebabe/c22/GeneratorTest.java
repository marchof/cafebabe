package org.jacoco.cafebabe.c22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.jacoco.cafebabe.test.Dumper;
import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link Generator}.
 */
class GeneratorTest {

	private MemoryClassLoader cl;

	@BeforeEach
	void before() {
		cl = new MemoryClassLoader();
	}

	@Test
	void generated_class_should_behave_correctly() throws Throwable {
		cl.add(Generator.create());

		Class<?> cls = cl.loadClass("MyException");
		MethodHandle wrapper = MethodHandles.lookup()
				.findStatic(cls, "wrap", MethodType.methodType(RuntimeException.class, Throwable.class));

		var e = (RuntimeException) MethodHandles.lookup()
				.findConstructor(cls, MethodType.methodType(void.class, Throwable.class))
				.invoke((Object) null);
		assertEquals(e, wrapper.invoke(e), "should not wrap");

		assertTrue(cls.isInstance(wrapper.invoke(new RuntimeException())), "should wrap");
	}

	@Test
	void generator_should_create_frames_correctly() {
		byte[] definition = Generator.create();
		byte[] expectedFrames = computeFrames(definition);
		assertEquals(Dumper.dump(expectedFrames), Dumper.dump(definition));
	}

	private byte[] computeFrames(byte[] definition) {
		var cr = new ClassReader(definition);
		var cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES) {
			@Override
			protected String getCommonSuperClass(String type1, String type2) {
				if ("java/lang/Throwable".equals(type1) && "MyException".equals(type2)) {
					return type1;
				}
				throw new UnsupportedOperationException(type1 + " " + type2);
			}
		};
		cr.accept(cw, 0);
		return cw.toByteArray();
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		Generator.create(new CheckClassAdapter(null));
	}

}
