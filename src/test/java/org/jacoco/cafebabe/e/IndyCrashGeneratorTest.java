package org.jacoco.cafebabe.e;

import org.jacoco.cafebabe.c14.IndyGenerator;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.util.CheckClassAdapter;

class IndyCrashGeneratorTest {

  @Test
  void generator_should_use_event_APIs_correctly() {
    IndyGenerator.create(new CheckClassAdapter(null));
  }

}
