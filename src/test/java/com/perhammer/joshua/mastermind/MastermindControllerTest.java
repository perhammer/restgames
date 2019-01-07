package com.perhammer.joshua.mastermind;

import org.junit.Test;

import java.util.Random;

public class MastermindControllerTest {

    @Test
    public void testSomething() {
        long seed = -255581124224435288L;
        Random random = new Random(seed);
        MastermindController mc = new MastermindController(random);


    }
}
