package org.jacoco.cafebabe.e;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.BASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.F_FULL;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.NEWARRAY;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.T_BOOLEAN;
import static org.objectweb.asm.Opcodes.V1_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class IndyCrashGenerator {

  public static void main(String[] args) throws IOException {
    Files.write(Paths.get("/tmp/IndyCrash.class"), create());
  }

  public static byte[] create() {
    ClassWriter writer = new ClassWriter(0);
    create(writer);
    return writer.toByteArray();
  }

  public static void create(ClassVisitor cv) {
    cv.visit(V1_8, ACC_PUBLIC, "IndyCrash", null, "java/lang/Object", null);

    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "main", "([Ljava/lang/String;)V", null, null);
    mv.visitCode();
    Label label = new Label();
    mv.visitLabel(label);
    mv.visitFrame(F_FULL, 0, null, 0, null);
    mv.visitTypeInsn(NEW, "IndyCrash");
    mv.visitInsn(DUP);
    mv.visitMethodInsn(INVOKESPECIAL, "IndyCrash", "<init>", "()V", false);
    mv.visitInsn(POP);
    mv.visitJumpInsn(GOTO, label);
    mv.visitMaxs(2, 1);
    mv.visitEnd();

    mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
    mv.visitCode();

    mv.visitInvokeDynamicInsn("bootstrap", "()[Z", HANDLE);
    mv.visitInsn(ICONST_0);
    mv.visitInsn(ICONST_1);
    mv.visitInsn(BASTORE);

    mv.visitVarInsn(ALOAD, 0);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    mv.visitInsn(RETURN);
    mv.visitMaxs(4, 1);
    mv.visitEnd();

    mv = cv.visitMethod(ACC_STATIC, "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null, null);
    mv.visitCode();
    mv.visitTypeInsn(NEW, "java/lang/invoke/ConstantCallSite");
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 0);
    mv.visitLdcInsn(Type.getType("LIndyCrash;"));
    mv.visitLdcInsn("data");
    mv.visitLdcInsn(Type.getType(boolean[].class));
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/invoke/MethodHandles$Lookup", "findStaticGetter", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;", false);
    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/invoke/ConstantCallSite", "<init>", "(Ljava/lang/invoke/MethodHandle;)V", false);
    mv.visitInsn(ARETURN);
    mv.visitMaxs(6, 3);
    mv.visitEnd();

    cv.visitField(ACC_STATIC, "data", "[Z", null, null);

    mv = cv.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
    mv.visitCode();
    mv.visitInsn(ICONST_1);
    mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
    mv.visitFieldInsn(PUTSTATIC, "IndyCrash", "data", "[Z");
    mv.visitInsn(RETURN);
    mv.visitMaxs(2, 0);
    mv.visitEnd();
  }

  private static Handle HANDLE = new Handle(H_INVOKESTATIC, "IndyCrash", "bootstrap",
    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false);

}
