package org.jacoco.cafebabe.c06;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.jacoco.cafebabe.test.MemoryClassLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExceptionHandlerGeneratorTest {

  private MemoryClassLoader cl;

  @BeforeEach
  void before() {
    cl = new MemoryClassLoader();
  }

  @Test
  void run() throws Exception {
    cl.add(ExceptionHandlerGenerator.create());

    Runnable runnable = cl.newInstance("ExceptionHandlerRunnable");

    RuntimeException e = assertThrows(RuntimeException.class, runnable::run);
    assertSame(RuntimeException.class, e.getCause().getClass());
    assertSame(IOException.class, e.getCause().getCause().getClass());
  }

}
