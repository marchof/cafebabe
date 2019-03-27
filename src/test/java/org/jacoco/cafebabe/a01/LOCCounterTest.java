package org.jacoco.cafebabe.a01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jacoco.cafebabe.test.ClassDefinition;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link LOCCounter}.
 */
class LOCCounterTest {

	@Test
	void should_count_lines() throws Exception {
		byte[] definition = ClassDefinition.get(SimpleTarget.class);

		assertEquals(1, LOCCounter.getLOC(definition));
	}

}
