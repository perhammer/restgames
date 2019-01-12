package com.perhammer.joshua.mastermind;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mastermind {

    private static final String WHITE_PEG = "○";
    private static final String BLACK_PEG = "●";

    public static void main(String args[]) throws IOException {
        while (true) {
            game();
        }
    }

    private static void game() throws IOException {
        MastermindController mc = new MastermindController();

        System.out.println("Blue, Green, Orange, Purple, Red, Yellow");

        System.out.println("####");

        List<MastermindColour> attempt;
        while (true) {
            Scanner sc = new Scanner(System.in);
            String line = sc.next();

            while (line.length()<4) {
                line = line+" ";
            }
            attempt = new ArrayList<>();
            for(int i=0; i!=4; i++) {
                attempt.add(MastermindColour.fromString(line.charAt(i)));
            }

            List<MastermindHint> hints = mc.addAttempt(attempt);
            String renderedHints = "";
            for(MastermindHint hint:hints) {
                if (MastermindHint.CORRECT_COLOUR.equals(hint)) {
                    renderedHints = renderedHints + WHITE_PEG;
                } else if (MastermindHint.CORRECT_COLOUR_AND_LOCATION.equals(hint)) {
                    renderedHints = renderedHints + BLACK_PEG;
                }
            }
            System.out.println("["+renderedHints+"]");

            if (mc.isFinished()) {
                System.out.println("Congratulations, you solved the code! Hit enter to play again...");
                System.in.read();
                return;
            }
        }
    }
}
