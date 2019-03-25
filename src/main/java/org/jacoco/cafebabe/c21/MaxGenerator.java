package org.jacoco.cafebabe.c21;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.F_FULL;
import static org.objectweb.asm.Opcodes.IF_ICMPLT;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INTEGER;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.V11;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * Simple control flow to determine the maximum of two integers.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.11.7">JVM
 *      Spec 2.11.7</a>
 */
class MaxGenerator {

	static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Max", null, "java/lang/Object",
				new String[] { "java/util/function/IntBinaryOperator" });

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "applyAsInt", "(II)I", null, null);
		mv.visitCode();
		mv.visitVarInsn(ILOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		Label lt = new Label();
		mv.visitJumpInsn(IF_ICMPLT, lt);

		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(IRETURN);

		mv.visitLabel(lt);
		mv.visitFrame(F_FULL, 3, new Object[] { "Max", INTEGER, INTEGER }, 0, new Object[0]);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitInsn(IRETURN);

		mv.visitMaxs(2, 3);
		mv.visitEnd();

		cv.visitEnd();
	}

}
