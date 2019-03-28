package org.jacoco.cafebabe.c59;

import static org.objectweb.asm.Opcodes.*;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a class that uses a dynamic constant and causes crash of JVM 11.
 *
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10">JVM
 *      Spec 4.4.10. The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.ldc">JVM
 *      Spec 6.5.ldc</a>
 * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8216970">JDK-8216970</a>
 */
class CondyCrashGenerator {

	public static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {
		cv.visit(V11, ACC_PUBLIC, "CondyCrash", null, "java/lang/Object", null);

		GeneratorSupport.addConstructor(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		mv.visitCode();
		mv.visitLdcInsn(new ConstantDynamic("foo", "[Z", HANDLE));
		mv.visitVarInsn(ASTORE, 0);
		mv.visitTypeInsn(NEW, "java/lang/Object");
		mv.visitVarInsn(ASTORE, 0);
		mv.visitInsn(ACONST_NULL);
		mv.visitMethodInsn(INVOKESTATIC, "CondyCrash", "main", "([Ljava/lang/String;)V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		addBootstrapMethod(cv);

		cv.visitEnd();
	}

	private static void addBootstrapMethod(ClassVisitor cv) {
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "bootstrap",
			"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)[Z", null, null);
		mv.visitCode();
		mv.visitInsn(ICONST_0);
		mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 3);
		mv.visitEnd();
	}

	private static Handle HANDLE = new Handle(H_INVOKESTATIC, "CondyCrash", "bootstrap",
			"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)[Z", false);

}
