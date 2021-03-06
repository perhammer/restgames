package com.perhammer.joshua.slidingpuzzle;

import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;

public class SlidingPuzzleControllerTest {


    @Test
    public void test3by3() throws IOException {
        Random random = new Random(-3854787941188383801L);

        SlidingPuzzleController spc = SlidingPuzzleControllerFactory.getFactory(random).get3by3game();
                //SlidingPuzzleController.get3by3game();

        SlidingPuzzleBoardAsciiRenderer renderer = new SlidingPuzzleBoardAsciiRenderer();
        spc.setRenderer(renderer);

        spc.scramble();

        assertTrue(!spc.isFinished());
    }

}
