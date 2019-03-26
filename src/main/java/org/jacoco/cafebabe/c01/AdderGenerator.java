package org.jacoco.cafebabe.c01;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.V11;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a simple class with a single static method
 * 
 * <pre>
 * public static int add(int a, int b);
 * </pre>
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.6">JVM
 *      Spec 4.6. Methods</a>
 */
class AdderGenerator {

	static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Adder", null, "java/lang/Object", null);
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "add", "(II)I", null, null);
		mv.visitCode();
		mv.visitVarInsn(ILOAD, 0);
		mv.visitVarInsn(ILOAD, 1);
		mv.visitInsn(IADD);
		mv.visitInsn(IRETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();

		cv.visitEnd();
	}

}
