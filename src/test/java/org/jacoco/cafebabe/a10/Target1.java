package org.jacoco.cafebabe.a10;

class Target1 {

	// assertInstructions(0)

	void nop() {
		return; // assertInstructions(1)
	}

	void hello() {
		Thread.yield(); // assertInstructions(1)
		System.out.println(); // assertInstructions(2)
		System.out.println("Hello Bytecode!"); // assertInstructions(3)
	}

	boolean not(boolean f) {
		return !f; // assertInstructions(6)
	}

}
