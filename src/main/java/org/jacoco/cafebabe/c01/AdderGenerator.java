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
 */
public class AdderGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

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
