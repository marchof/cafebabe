package org.jacoco.cafebabe.c05;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.V11;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a <code>Runnable</code> implementation which throws a checked
 * exception.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.10">JVM
 *      Spec 2.10</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.athrow">JVM
 *      Spec 6.5.athrow</a>
 */
public class CheckedExceptionGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "CheckedExceptionRunnable", null, "java/lang/Object",
				new String[] { "java/lang/Runnable" });

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
		mv.visitCode();
		mv.visitTypeInsn(NEW, "java/io/IOException");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "()V", false);
		mv.visitInsn(ATHROW);
		mv.visitMaxs(2, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

}
