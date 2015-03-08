package map.intint;

import junit.framework.TestCase;
import tests.MapTestRunner;
import tests.maptests.IMapTest;
import tests.maptests.article_examples.BaseIntIntMapTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseIntIntMapUnitTest extends TestCase {

    abstract protected IntIntMap makeMap( final int size, final float fillFactor );

    public void testPut()
    {
        final IntIntMap map = makeMap(100, 0.5f);
        for ( int i = 0; i < 100000; ++i )
        {
            map.put(i, i);
            assertEquals(i + 1, map.size());
            assertEquals(i, map.get( i ));
        }
        //now check the final state
        for ( int i = 0; i < 100000; ++i )
            assertEquals(i, map.get( i ));
    }

    public void testPutNegative()
    {
        final IntIntMap map = makeMap(100, 0.5f);
        for ( int i = 0; i < 100000; ++i )
        {
            map.put(-i, -i);
            assertEquals(i + 1, map.size());
            assertEquals(-i, map.get( -i ));
        }
        //now check the final state
        for ( int i = 0; i < 100000; ++i )
            assertEquals(-i, map.get( -i ));
    }

    public void testPutRandom()
    {
        final int SIZE = 100 * 1000;
        final Set<Integer> set = new HashSet<>( SIZE );
        final int[] vals = new int[ SIZE ];
        while ( set.size() < SIZE )
            set.add( ThreadLocalRandom.current().nextInt() );
        int i = 0;
        for ( final Integer v : set )
            vals[ i++ ] = v;
        final IntIntMap map = makeMap(100, 0.5f);
        for ( i = 0; i < vals.length; ++i )
        {
            map.put(vals[ i ], vals[ i ]);
            assertEquals(i + 1, map.size());
            assertEquals(vals[ i ], map.get( vals[ i ] ));
        }
        //now check the final state
        for ( i = 0; i < vals.length; ++i )
            assertEquals(vals[ i ], map.get( vals[ i ] ));
    }

    public void testRemove()
    {
        final IntIntMap map = makeMap(100, 0.5f);
        int addCnt = 0, removeCnt = 0;
        for ( int i = 0; i < 100000; ++i )
        {
            assertEquals(0, map.put(addCnt, addCnt));
            addCnt++;
            assertEquals(0, map.put(addCnt, addCnt));
            addCnt++;
            assertEquals(removeCnt, map.remove(removeCnt));
            removeCnt++;

            assertEquals(i + 1, map.size()); //map grows by one element on each iteration
        }
        for ( int i = removeCnt; i < addCnt; ++i )
            assertEquals(i, map.get( i ));
    }

    public void test1()
    {
        final BaseIntIntMapTest test = new BaseIntIntMapTest() {
            @Override
            public IntIntMap makeMap(int size, float fillFactor) {
                return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
            }
        };
        final IMapTest test1 = test.getTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }

    public void test2()
    {
        final BaseIntIntMapTest test = new BaseIntIntMapTest() {
            @Override
            public IntIntMap makeMap(int size, float fillFactor) {
                return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
            }
        };
        final IMapTest test1 = test.putTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10 * 1000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }

    public void test3()
    {
        final BaseIntIntMapTest test = new BaseIntIntMapTest() {
            @Override
            public IntIntMap makeMap(int size, float fillFactor) {
                return BaseIntIntMapUnitTest.this.makeMap(size, fillFactor);
            }
        };
        final IMapTest test1 = test.removeTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }
}
