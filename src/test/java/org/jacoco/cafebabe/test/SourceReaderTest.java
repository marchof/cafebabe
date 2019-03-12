package org.jacoco.cafebabe.test;

//target()
//
//target()

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SourceReaderTest {

	private List<Integer> lines = new ArrayList<>();

	public void target(int line) {
		lines.add(line);
	}

	@Test
	void should_execute_comments() throws Exception {
		SourceReader reader = new SourceReader(SourceReaderTest.class);

		reader.executeComments(this);

		assertEquals(List.of(3, 5), lines);
	}

}
