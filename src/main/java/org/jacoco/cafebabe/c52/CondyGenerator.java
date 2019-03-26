package org.jacoco.cafebabe.c52;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.V11;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a class which uses a dynamic constant.
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10">JVM
 *      Spec 4.4.10. The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.ldc">JVM
 *      Spec 6.5.ldc</a>
 */
public class CondyGenerator {

	static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Condy", null, "java/lang/Object", new String[] { "java/util/function/Supplier" });

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "get", "()Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitLdcInsn(new ConstantDynamic("foo", "Ljava/time/LocalDate;", HANDLE));
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

	/**
	 * The bootstrap method used at runtime to calculate the constant.
	 */
	public static LocalDate bootstrap(MethodHandles.Lookup lookup, String name, Class<?> type) {
		return LocalDate.now();
	}

	private static Handle HANDLE = new Handle(H_INVOKESTATIC, "org/jacoco/cafebabe/c52/CondyGenerator", "bootstrap",
			"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/Class;)Ljava/time/LocalDate;", false);

}
