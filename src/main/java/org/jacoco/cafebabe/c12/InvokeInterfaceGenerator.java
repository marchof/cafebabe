package org.jacoco.cafebabe.c12;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.V11;

import java.util.function.Supplier;

import org.jacoco.cafebabe.util.GeneratorSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/**
 * Creates a {@link java.util.function.Function} implementation that accepts {@link Supplier} and
 * calls its {@link Supplier#get()} method.
 */
public class InvokeInterfaceGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {

		cv.visit(V11, ACC_PUBLIC, "InvokeInterface", null, "java/lang/Object", new String[]{"java/util/function/Function"});

		GeneratorSupport.defaultInit(cv);

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "apply", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
		mv.visitCode();

		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/function/Supplier", "get", "()Ljava/lang/Object;", true);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 2);
		mv.visitEnd();

		cv.visitEnd();
	}

}
