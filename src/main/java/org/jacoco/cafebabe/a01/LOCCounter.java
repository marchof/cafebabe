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
 *      Spec 4.7.12. The LineNumberTable Attribute</a>
 */
class LOCCounter {

	static int getLOC(byte[] definition) {
		var reader = new ClassReader(definition);
		var visitor = new LOCClassVisitor();
		reader.accept(visitor, 0);
		return visitor.getLOCs();
	}

	private static final class LOCClassVisitor extends ClassVisitor {
		private final Set<Integer> lines = new HashSet<>();

		private LOCClassVisitor() {
			super(Opcodes.ASM7);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
				String[] exceptions) {
			return new MethodVisitor(Opcodes.ASM7) {
				@Override
				public void visitLineNumber(int line, Label start) {
					lines.add(line);
				}
			};
		}

		int getLOCs() {
			return lines.size();
		}
	}

}
