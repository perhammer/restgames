package com.perhammer.joshua.slidingpuzzle;

import java.util.Random;
import java.util.Scanner;

public class SlidingPuzzle {

    public static void main(String args[]) throws Exception {
        Random random = new Random(-3854787941188383801L);

        SlidingPuzzleController spc = SlidingPuzzleControllerFactory.getFactory(random).get3by3game();

        SlidingPuzzleBoardAsciiRenderer renderer = new SlidingPuzzleBoardAsciiRenderer();
        spc.setRenderer(renderer);

        spc.scramble();

        while(true) {
            System.out.println(" w");
            System.out.println("asd");
            System.out.println();

            System.out.println(renderer.render());


            Scanner sc = new Scanner(System.in);
            int ch = sc.next().charAt(0);

            SlidingMove move = null;
            switch (ch) {
                case 'w':
                    move = SlidingMove.NORTH;
                    break;
                case 'a':
                    move = SlidingMove.WEST;
                    break;
                case 's':
                    move = SlidingMove.SOUTH;
                    break;
                case 'd':
                    move = SlidingMove.EAST;
                    break;
                case '`':
                    System.exit(0);
                default:
                    break;
            }
            if (move!=null) {
                spc.slide(move);
            }

            if (spc.isFinished()) {
                System.out.println(renderer.render());
                System.out.println("You won! Hit enter to play again...");
                System.in.read();
                spc.scramble();
            }
        }
    }
}
