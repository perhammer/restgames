package com.perhammer.joshua.twentyfortyeight;

import java.util.List;
import java.util.Stack;

public class NumberFusingStack extends Stack<Integer> {

    public static final Integer UNFUSEABLE = null;

    @Override
    public Integer push(Integer item) {
        if (isEmpty()) {
            return super.push(item);
        }

        Integer previous = super.peek();
        if (previous!=UNFUSEABLE && previous.equals(item)) {
            super.pop();
            return super.push( item + previous );
        }
        return super.push(item);
    }

    @Override
    public synchronized List<Integer> subList(int fromIndex, int toIndex) {
        List<Integer> list = super.subList(fromIndex, toIndex);

        while (list.contains(UNFUSEABLE)) {
            list.remove(UNFUSEABLE);
        }

        return list;
    }
}
