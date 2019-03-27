package org.jacoco.cafebabe.c14;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.V11;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a class which uses a dynamic call site (an invokedynamic
 * instruction).
 * 
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10">JVM
 *      Spec 4.4.10. The CONSTANT_Dynamic_info and CONSTANT_InvokeDynamic_info Structures</a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html#jvms-6.5.invokedynamic">JVM
 *      Spec 6.5.invokedynamic</a>
 */
public class IndyGenerator {

	static MutableCallSite callSite = new MutableCallSite(MethodHandles.constant(String.class, "Hello"));

	static byte[] create() {
		var writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Indy", null, "java/lang/Object", new String[]{"java/util/function/Supplier"});

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "get", "()Ljava/lang/Object;", null, null);
		mv.visitCode();

		var handle = new Handle(H_INVOKESTATIC, "org/jacoco/cafebabe/c14/IndyGenerator", "bootstrap",
			"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false);
		mv.visitInvokeDynamicInsn("get", "()Ljava/lang/String;", handle);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		cv.visitEnd();
	}

	/**
	 * The bootstrap method used at runtime to link dynamic call site.
	 */
	public static CallSite bootstrap(MethodHandles.Lookup caller, String name, MethodType type) {
		return callSite;
	}

}
