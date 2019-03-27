package org.jacoco.cafebabe.a10;

/**
 * Example class used in {@link InstructionsCounterTest}.
 */
class ComplexTarget { // assertInstructions(0)

	ComplexTarget() { // assertInstructions(2)
	} // assertInstructions(1)

	void nop() { // assertInstructions(0)
	} // assertInstructions(1)

	boolean not(boolean f) {
		return !f; // assertInstructions(6)
	}

	static final String MSG1 = "Hello, JPoint!"; // assertInstructions(0)

	static final String MSG2 = String.format("Hello, %s", "JPoint"); // assertInstructions(10)

}
