package org.jacoco.cafebabe.c02;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V11;

import java.util.function.IntBinaryOperator;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a simple class implementing a {@link IntBinaryOperator} which adds
 * two integers.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.1">JVM
 *      Spec 4.1. The ClassFile Structure</a>
 */
class AdderGenerator {

	static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Adder", null, "java/lang/Object",
				new String[] { "java/util/function/IntBinaryOperator" });

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		mv = cv.visitMethod(ACC_PUBLIC, "applyAsInt", "(II)I", null, null);
		mv.visitCode();
		mv.visitVarInsn(ILOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(IADD);
		mv.visitInsn(IRETURN);
		mv.visitMaxs(2, 3);
		mv.visitEnd();

		cv.visitEnd();
	}

}
