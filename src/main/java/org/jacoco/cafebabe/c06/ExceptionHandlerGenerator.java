package org.jacoco.cafebabe.c06;

import static org.objectweb.asm.Opcodes.*;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a {@link java.util.function.Predicate} implementation that accepts
 * {@link Runnable} and returns {@code true} when execution of its
 * {@link Runnable#run()} method throws exception, {@code false} otherwise.
 *
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.10">JVM
 *      Spec 2.10. Exceptions</a>
 */
class ExceptionHandlerGenerator {

	static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {
		cv.visit(V1_5, ACC_PUBLIC, "ExceptionHandler", null, "java/lang/Object",
				new String[]{"java/util/function/Predicate"});

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "test", "(Ljava/lang/Object;)Z", null, null);
		mv.visitCode();

		var start = new Label();
		var end = new Label();
		var handler = new Label();
		var exit = new Label();
		mv.visitTryCatchBlock(start, end, handler, "java/lang/Exception");

		mv.visitLabel(start);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/lang/Runnable", "run", "()V", true);
		mv.visitLabel(end);
		mv.visitInsn(ICONST_0);
		mv.visitJumpInsn(GOTO, exit);

		mv.visitLabel(handler);
		mv.visitInsn(POP);
		mv.visitInsn(ICONST_1);

		mv.visitLabel(exit);
		mv.visitInsn(IRETURN);

		mv.visitMaxs(1, 2);
		mv.visitEnd();

		cv.visitEnd();
	}

}
