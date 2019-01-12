package com.perhammer.joshua;

import com.perhammer.joshua.twentyfortyeight.GameOverException;
import com.perhammer.joshua.twentyfortyeight.Twenty48Board;
import com.perhammer.joshua.twentyfortyeight.Twenty48Controller;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class Twenty48BoardTest {

    @Test
    @Ignore
    public void test() throws GameOverException {
        Twenty48Board board = new Twenty48Board();

        board.addTile();
        board.addTile();
        board.addTile();
        board.addTile();

        System.out.println("Before sliding left");
        board.render(System.out);

        board.slideLeft();
        board.addTile();

        System.out.println("After sliding left");
        board.render(System.out);

        board.slideRight();
        board.addTile();

        System.out.println("After sliding right");
        board.render(System.out);

        board.slideDown();
        board.addTile();

        System.out.println("After sliding down");
        board.render(System.out);

        board.slideUp();
        board.addTile();

        System.out.println("After sliding up");
        board.render(System.out);

    }

    @Test
    @Ignore
    public void testA() throws Throwable {
        Twenty48Board board = new Twenty48Board();

        board.addTile();
        board.addTile();
        board.addTile();

        board.render(System.out);

        System.out.println();
        board.slideDown();

        board.render(System.out);
    }

    @Test
    public void testB() {
        Twenty48Controller ctrl = new Twenty48Controller();

        int iterations  = 2500;

        while(iterations-->0 && !ctrl.isFinished()) {
            ctrl.render(System.out);
            ctrl.slideLeft();

            System.out.println();
        }
        System.out.println(ctrl.isFinished()+" "+iterations);
    }
}
