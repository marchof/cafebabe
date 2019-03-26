package org.jacoco.cafebabe.c53;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a class which uses {@code putstatic} for {@code final} field outside of static initializer.
 *
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.9.2">JVM
 *      Spec 2.9.2. Class Initialization Methods</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.putstatic">JVM
 *      Spec 6.5.putstatic</a>
 */
class PutStaticGenerator {

	static byte[] create(int version) {
		ClassWriter writer = new ClassWriter(0);
		create(version, writer);
		return writer.toByteArray();
	}

	static void create(int version, ClassVisitor cv) {

		cv.visit(version, ACC_PUBLIC, "Example", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

		GeneratorSupport.defaultInit(cv);

		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST", "I", null, 42);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
		mv.visitCode();

		mv.visitLdcInsn(13);
		mv.visitFieldInsn(PUTSTATIC, "Example", "CONST", "I");
		mv.visitInsn(RETURN);

		mv.visitMaxs(1, 1);
		mv.visitEnd();

		cv.visitEnd();

	}

}
