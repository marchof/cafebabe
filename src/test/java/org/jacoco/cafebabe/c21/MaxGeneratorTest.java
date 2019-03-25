package org.jacoco.cafebabe.c21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jacoco.cafebabe.test.Dumper;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

class MaxGeneratorTest {

	@Test
	void generator_should_create_frames_correctly() {
		byte[] definition = MaxGenerator.create();
		byte[] expectedFrames = computeFrames(definition);
		assertEquals(Dumper.dump(expectedFrames), Dumper.dump(definition));
	}

	private byte[] computeFrames(byte[] definition) {
		ClassReader cr = new ClassReader(definition);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(cw, 0);
		return cw.toByteArray();
	}

	@Test
	void generator_should_use_event_APIs_correctly() {
		MaxGenerator.create(new CheckClassAdapter(null));
	}

}
