package org.jacoco.cafebabe.c02;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V11;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a simple class implementing the interface
 * <code>java.util.function.Supplier</code>.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.1">JVM
 *      Spec 4.1</a>
 */
public class HelloSupplierGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "HelloSupplier", null, "java/lang/Object",
				new String[] { "java/util/function/Supplier" });

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		mv = cv.visitMethod(ACC_PUBLIC, "get", "()Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitLdcInsn("Hello");
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

}
