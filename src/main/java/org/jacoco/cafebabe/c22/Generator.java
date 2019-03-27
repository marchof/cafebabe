package org.jacoco.cafebabe.c22;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * Demonstrates why {@link ClassWriter#COMPUTE_FRAMES} is bad.
 *
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.4">JVM
 *      Spec 4.7.4. The StackMapTable Attribute</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.10.1">JVM
 *      Spec 4.10.1. Verification by Type Checking</a>
 */
class Generator {

	static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	/**
	 * <pre>
	 *   static RuntimeException wrap(Throwable t) {
	 *     return (RuntimeException) (t instanceof MyException ? t : new MyException(t));
	 *   }
	 * </pre>
	 */
	static void create(ClassVisitor cv) {
		cv.visit(V11, ACC_PUBLIC, "MyException", null, "java/lang/RuntimeException", null);
		addConstructor(cv);

		MethodVisitor mv = cv.visitMethod(ACC_STATIC | ACC_PUBLIC, "wrap",
				"(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;", null, null);
		mv.visitCode();

		mv.visitVarInsn(ALOAD, 0);
		mv.visitTypeInsn(INSTANCEOF, "MyException");
		var l = new Label();
		mv.visitJumpInsn(IFEQ, l);

		mv.visitVarInsn(ALOAD, 0);
		var r = new Label();
		mv.visitJumpInsn(GOTO, r);

		mv.visitLabel(l);
		mv.visitFrame(F_FULL, 1, new Object[]{"java/lang/Throwable"}, 0, new Object[]{});
		mv.visitTypeInsn(NEW, "MyException");
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "MyException", "<init>", "(Ljava/lang/Throwable;)V", false);

		mv.visitLabel(r);
		mv.visitFrame(F_FULL, 1, new Object[]{"java/lang/Throwable"}, 1, new Object[]{"java/lang/Throwable"});
		mv.visitTypeInsn(CHECKCAST, "java/lang/RuntimeException");
		mv.visitInsn(ARETURN);

		mv.visitMaxs(3, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

	private static void addConstructor(ClassVisitor cv) {
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/Throwable;)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/Throwable;)V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
	}

}
