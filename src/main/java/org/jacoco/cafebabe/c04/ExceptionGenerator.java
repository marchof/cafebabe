package org.jacoco.cafebabe.c04;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.V11;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a <code>Runnable</code> which throws exception. The class
 * file is created with debug information so that the exception has a source and
 * line number reference.
 * 
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.10">JVM
 *      Spec 2.10. Exceptions</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.5">JVM
 *      Spec 4.7.5. The Exceptions Attribute</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.10">JVM
 *      Spec 4.7.10. The SourceFile Attribute</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.12">JVM
 *      Spec 4.7.12. The LineNumberTable Attribute</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.athrow">JVM
 *      Spec 6.5.athrow</a>
 */
class ExceptionGenerator {

	static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "ExceptionRunnable", null, "java/lang/Object", new String[] { "java/lang/Runnable" });
		cv.visitSource("AnyNameYouLike.c", null);

		GeneratorSupport.addConstructor(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
		mv.visitCode();

		var l = new Label();
		mv.visitLabel(l);
		mv.visitLineNumber(12345, l);

		mv.visitTypeInsn(NEW, "java/io/IOException");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "()V", false);
		mv.visitInsn(ATHROW);
		mv.visitMaxs(2, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

}
