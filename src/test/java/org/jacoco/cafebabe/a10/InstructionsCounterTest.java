package org.jacoco.cafebabe.a10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.jacoco.cafebabe.test.ClassDefinition;
import org.jacoco.cafebabe.test.SourceReader;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link InstructionsCounter}.
 */
public class InstructionsCounterTest {

	private Map<Integer, Integer> instructions;

	@Test
	void simple_target() throws Exception {
		run(SimpleTarget.class);
	}

	void run(Class<?> target) throws Exception {
		byte[] definition = ClassDefinition.get(target);
		instructions = InstructionsCounter.getInstructionsPerLine(definition);

		var reader = new SourceReader(target);
		reader.executeComments(this);
	}

	public void assertInstructions(int line, int expected) {
		assertEquals(expected, instructions.getOrDefault(line, 0), "Line " + line);
	}

}
