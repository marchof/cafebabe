package org.jacoco.cafebabe.a01;

import java.time.LocalDate;

/**
 * Example class used in {@link LOCCounterTest}.
 */
public class LOCCounterTestTarget { // #1 implicit default constructor

	LocalDate today = LocalDate.now(); // #2 part of the default constructor

	LocalDate getToday() {
		return today; // #3
	}
}
