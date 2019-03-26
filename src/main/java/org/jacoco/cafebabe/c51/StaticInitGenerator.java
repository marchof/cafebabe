package org.jacoco.cafebabe.c51;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V11;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Creates a class with constant field initialized with an static initializer.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.9.2">JVM
 *      Spec 2.9.2. Class Initialization Methods</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-5.html#jvms-5.5">JVM
 *      Spec 5.5. Initialization</a>
 */
class StaticInitGenerator {

	static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {
		cv.visit(V11, ACC_PUBLIC, "Constants", null, "java/lang/Object", null);

		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST", "Ljava/lang/Object;", null, null);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "<clinit>", "()V", null, null);
		mv.visitCode();
		mv.visitTypeInsn(NEW, "java/lang/Object");
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>",
			"()V", false);
		mv.visitFieldInsn(PUTSTATIC, "Constants", "CONST", "Ljava/lang/Object;");
		mv.visitInsn(RETURN);
		mv.visitMaxs(2, 0);
		mv.visitEnd();

		cv.visitEnd();
	}

}
