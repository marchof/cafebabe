package org.jacoco.cafebabe.c59;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Unit test for {@link CondyCrashGenerator}.
 */
class CondyCrashGeneratorTest {

	@Test
	void generator_should_use_event_APIs_correctly() {
		CondyCrashGenerator.create(new CheckClassAdapter(null));
	}

}
