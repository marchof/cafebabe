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
 * Creates a class which uses a dynamic call site (an invokedynamic instruction).
 */
public class IndyGenerator {

	static MutableCallSite callSite = new MutableCallSite(MethodHandles.constant(String.class, "Hello"));

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "Indy", null, "java/lang/Object", new String[]{"java/util/function/Supplier"});

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "get", "()Ljava/lang/Object;", null, null);
		mv.visitCode();

		Handle handle = new Handle(H_INVOKESTATIC, "org/jacoco/cafebabe/c14/IndyGenerator", "bootstrap",
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
