package org.jacoco.cafebabe.c03;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.DUP_X1;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.V11;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a counting <code>IntSupplier</code> which holds the current counter
 * value as an instance field.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.5">JVM
 *      Spec 4.5</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.getfield">JVM
 *      Spec 6.5.getfield</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.putfield">JVM
 *      Spec 6.5.putfield</a>
 */
public class CounterGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Counter", null, "java/lang/Object",
				new String[] { "java/util/function/IntSupplier" });

		cv.visitField(ACC_PRIVATE, "counter", "I", null, 0);

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getAsInt", "()I", null, null);
		mv.visitCode();

		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);

		mv.visitFieldInsn(GETFIELD, "Counter", "counter", "I");
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);

		mv.visitInsn(DUP_X1);
		mv.visitFieldInsn(PUTFIELD, "Counter", "counter", "I");

		mv.visitInsn(IRETURN);

		mv.visitMaxs(3, 1);
		mv.visitEnd();
		cv.visitEnd();
	}

}
