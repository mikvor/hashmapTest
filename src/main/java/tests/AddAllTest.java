package tests;

import com.carrotsearch.hppc.IntOpenHashSet;

/**
 * Created by Mike on 3/01/2015.
 */
public class AddAllTest {
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();

        final IntOpenHashSet a = new com.carrotsearch.hppc.IntOpenHashSet();
        for( int i = 10000000; i-- != 0; ) a.add(i);
        IntOpenHashSet b = new com.carrotsearch.hppc.IntOpenHashSet(a.size());
        b.addAll(a);
        b = new com.carrotsearch.hppc.IntOpenHashSet();
        b.addAll(a);

        final long time = System.currentTimeMillis() - start;
        System.out.println( time / 1000.0 );
        System.out.println( b.size() );
    }
}
