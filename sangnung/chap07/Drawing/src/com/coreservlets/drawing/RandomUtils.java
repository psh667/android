package com.coreservlets.drawing;

import java.util.Random;

public class RandomUtils {
    private static Random r = new Random();

    /** Returns a random int from 0 to range-1. So, randomInt(4) returns any of 0,
     *  1, 2, or 3.
     */

    public static int randomInt(int range) {
        return(r.nextInt(range));
    }

    /** Returns a random index of an array. */

    public static int randomIndex(Object[] array) {
        return(randomInt(array.length));
    }

    /** Returns a random element from an array. Uses generics, so no typecast is
     *  required for the return value.
     */

    public static <T> T randomElement(T[] array) {
        return(array[randomIndex(array)]);
    }
    
    /** Returns a random float between 0 (inclusive) and n (exclusive). */
    
    public static float randomFloat(int n) {
        return((float)Math.random()*n);
    }
}
