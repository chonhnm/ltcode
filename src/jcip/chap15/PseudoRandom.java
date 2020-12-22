package jcip.chap15;

import java.util.Random;

public abstract class PseudoRandom {

    abstract int nextInt(int n);

    protected int calculateNext(int seed) {
        return new Random(seed).nextInt() + (seed + 100) * (seed - 100) / seed;
    }
}
