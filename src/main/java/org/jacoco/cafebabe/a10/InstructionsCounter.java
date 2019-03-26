package org.jacoco.cafebabe.a10;

import static org.objectweb.asm.Opcodes.ASM7;
import static org.objectweb.asm.tree.AbstractInsnNode.FRAME;
import static org.objectweb.asm.tree.AbstractInsnNode.LABEL;
import static org.objectweb.asm.tree.AbstractInsnNode.LINE;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
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
		ClassReader reader = new ClassReader(definition);

		Map<Integer, Integer> instructions = new HashMap<>();
		reader.accept(new CountingClassVisitor(instructions), 0);

		return instructions;
	}

	private static final class CountingClassVisitor extends ClassVisitor {
		private final Map<Integer, Integer> instructionCounters;

		private CountingClassVisitor(Map<Integer, Integer> instructionCounters) {
			super(ASM7);
			this.instructionCounters = instructionCounters;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
				String[] exceptions) {
			return new MethodNode(ASM7, access, name, descriptor, signature, exceptions) {
				@Override
				public void visitEnd() {
					int currentLine = 0;
					for (var i = instructions.getFirst(); i != null; i = i.getNext()) {
						switch (i.getType()) {
						case LINE:
							currentLine = ((LineNumberNode) i).line;
							break;
						case FRAME:
						case LABEL:
							// not actually instructions
							break;
						default:
							instructionCounters.merge(currentLine, 1, (a, b) -> a + b);
						}
					}
				}
			};
		}
	}

}
