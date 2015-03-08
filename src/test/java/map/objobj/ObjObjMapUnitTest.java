package map.objobj;

import junit.framework.TestCase;
import tests.MapTestRunner;
import tests.maptests.IMapTest;
import tests.maptests.article_examples.ObjObjMapTest;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ObjObjMapUnitTest extends TestCase {
    private ObjObjMap<Integer, Integer> makeMap( final int size, final float fillFactor )
    {
        return new ObjObjMap<>( size, fillFactor );
    }

    public void testPut()
    {
        final ObjObjMap<Integer, Integer> map = makeMap(100, 0.5f);
        for ( int i = 0; i < 100000; ++i )
        {
            map.put(i, i);
            assertEquals(i + 1, map.size());
            assertEquals(Integer.valueOf(i), map.get( i ));
        }
        //now check the final state
        for ( int i = 0; i < 100000; ++i )
            assertEquals(Integer.valueOf(i), map.get( i ));
    }

    public void testPutNegative()
    {
        final ObjObjMap<Integer, Integer> map = makeMap(100, 0.5f);
        for ( int i = 0; i < 100000; ++i )
        {
            map.put(-i, -i);
            assertEquals(i + 1, map.size());
            assertEquals(Integer.valueOf(-i), map.get( -i ));
        }
        //now check the final state
        for ( int i = 0; i < 100000; ++i )
            assertEquals(Integer.valueOf(-i), map.get( -i ));
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
        final ObjObjMap<Integer, Integer> map = makeMap(100, 0.5f);
        for ( i = 0; i < vals.length; ++i )
        {
            map.put(vals[ i ], vals[ i ]);
            assertEquals(i + 1, map.size());
            assertEquals(Integer.valueOf( vals[ i ] ), map.get( vals[ i ] ));
        }
        //now check the final state
        for ( i = 0; i < vals.length; ++i )
            assertEquals(Integer.valueOf( vals[ i ] ), map.get( vals[ i ] ));
    }

    public void testRemove()
    {
        final ObjObjMap<Integer, Integer> map = makeMap(100, 0.5f);
        int addCnt = 0, removeCnt = 0;
        for ( int i = 0; i < 100000; ++i )
        {
            assertNull(map.put(addCnt, addCnt));
            addCnt++;
            assertNull(map.put(addCnt, addCnt));
            addCnt++;
            assertEquals(Integer.valueOf(removeCnt), map.remove(removeCnt));
            removeCnt++;

            assertEquals(i + 1, map.size()); //map grows by one element on each iteration
        }
        for ( int i = removeCnt; i < addCnt; ++i )
            assertEquals(Integer.valueOf(i), map.get( i ));
    }

    public void test1()
    {
        final ObjObjMapTest test = new ObjObjMapTest();
        final IMapTest test1 = test.getTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }

    public void test2()
    {
        final ObjObjMapTest test = new ObjObjMapTest();
        final IMapTest test1 = test.putTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10 * 1000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }

    public void test3()
    {
        final ObjObjMapTest test = new ObjObjMapTest();
        final IMapTest test1 = test.removeTest();
        final int[] keys = MapTestRunner.KeyGenerator.getKeys( 10000 );
        test1.setup( keys, 0.5f, 2 );
        test1.test();
    }

}
