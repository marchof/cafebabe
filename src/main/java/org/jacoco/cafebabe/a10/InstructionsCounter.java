package org.jacoco.cafebabe.a10;

import static org.objectweb.asm.tree.AbstractInsnNode.FRAME;
import static org.objectweb.asm.tree.AbstractInsnNode.LABEL;
import static org.objectweb.asm.tree.AbstractInsnNode.LINE;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Simple bytecode analysis to count the bytecode instructions per line.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.12">JVM
 *      Spec 4.7.12. The LineNumberTable Attribute</a>
 */
class InstructionsCounter {

	static Map<Integer, Integer> getInstructionsPerLine(byte[] definition) {
		ClassNode classNode = new ClassNode();
		new ClassReader(definition).accept(classNode, 0);

		Map<Integer, Integer> instructions = new HashMap<>();
		classNode.methods.forEach(m -> count(m, instructions));

		return instructions;
	}

	private static void count(MethodNode m, Map<Integer, Integer> instructions) {
		int currentLine = 0;
		for (var i = m.instructions.getFirst(); i != null; i = i.getNext()) {
			switch (i.getType()) {
			case LINE:
				currentLine = ((LineNumberNode) i).line;
				break;
			case FRAME:
			case LABEL:
				// not actually instructions
				break;
			default:
				instructions.merge(currentLine, 1, (a, b) -> a + b);
			}
		}
	}

}
