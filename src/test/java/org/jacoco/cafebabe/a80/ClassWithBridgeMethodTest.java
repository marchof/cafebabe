package org.jacoco.cafebabe.a80;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

class ClassWithBridgeMethodTest {

	@Test
	void jvm_should_execute() {
		new ClassWithBridgeMethod().call();
	}

	@Test
	void asm_should_parse() throws Exception {
		new ClassReader(ClassWithBridgeMethod.class.getName())
			.accept(new ClassNode(), 0);
	}

}
