package org.jacoco.cafebabe.a01;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Simple bytecode analysis to count the executable source lines a given class
 * file is compiled from.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.12">JVM
 *      Spec 4.7.12</a>
 */
class LOCCounter {

	static int getLOC(byte[] definition) {
		ClassReader reader = new ClassReader(definition);

		Set<Integer> lines = new HashSet<>();
		reader.accept(new LOCClassVisitor(lines), 0);

		return lines.size();
	}

	private static final class LOCClassVisitor extends ClassVisitor {
		private final Set<Integer> lines;

		private LOCClassVisitor(Set<Integer> lines) {
			super(Opcodes.ASM7);
			this.lines = lines;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
				String[] exceptions) {
			return new LOCMethodVisitor(lines);
		}
	}

	private static class LOCMethodVisitor extends MethodVisitor {

		private Set<Integer> lines;

		public LOCMethodVisitor(Set<Integer> lines) {
			super(Opcodes.ASM7);
			this.lines = lines;
		}

		@Override
		public void visitLineNumber(int line, Label start) {
			lines.add(line);
		}
	}

}
