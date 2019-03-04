package org.jacoco.cafebabe.c06;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASTORE;
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

public class ExceptionHandlerGenerator {

  public static byte[] create() {
    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    create(writer);
    return writer.toByteArray();
  }

  public static void create(ClassVisitor cv) {
    cv.visit(V11, ACC_PUBLIC, "ExceptionHandlerRunnable", null, "java/lang/Object",
      new String[]{"java/lang/Runnable"});

    GeneratorSupport.defaultInit(cv);

    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
    mv.visitCode();

    Label l0 = new Label();
    Label l1 = new Label();
    Label h0 = new Label();
    Label h1 = new Label();
    mv.visitTryCatchBlock(l0, h0, h0, null);
    mv.visitTryCatchBlock(l1, h1, h1, null);

    mv.visitLabel(l0);
    mv.visitTypeInsn(NEW, "java/io/IOException");
    mv.visitInsn(DUP);
    mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "()V", false);
    mv.visitLabel(l1);
    mv.visitInsn(ATHROW);

    mv.visitLabel(h0);
    genWrap(mv);
    mv.visitInsn(ATHROW);

    mv.visitLabel(h1);
    genWrap(mv);
    mv.visitInsn(ATHROW);

    mv.visitMaxs(0, 0);
    mv.visitEnd();

    cv.visitEnd();

  }

  private static void genWrap(MethodVisitor mv) {
    mv.visitVarInsn(ASTORE, 1);
    mv.visitTypeInsn(NEW, "java/lang/RuntimeException");
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/Throwable;)V", false);
  }

}
