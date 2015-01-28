package tests;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;
import tests.maptests.identity_object.*;
import tests.maptests.object.*;
import tests.maptests.object_prim.*;
import tests.maptests.prim_object.*;
import tests.maptests.primitive.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/*
Master plan:
1) add following test cases:
1.1) 2 added, 1 (previously inserted) removed - until reached the map size
 */

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapTestRunner {
    private static final boolean BILLION_TEST = false;

    private static final int START_SIZE = BILLION_TEST ? 1000 * 1000 * 1000 : 10 * 1000;
    private static final int TOTAL_SIZE = BILLION_TEST ? 1000 * 1000 * 1000 : 100 * 1000 * 1000;
    private final static int[] MAP_SIZES;

    static {
        MAP_SIZES = new int[(int) (Math.log10( TOTAL_SIZE ) - Math.log10( START_SIZE ) + 1)];
        int start = START_SIZE;
        int p = 0;
        while ( start <= TOTAL_SIZE )
        {
            MAP_SIZES[ p++ ] = start;
            start *= 10;
        }
    }

    private static final float FILL_FACTOR = 0.5f;
    //share of unsuccessful get operations (approx)
    //increase this value significantly (Integer.MAX_VALUE is a good candidate) to revert to the original "always successful get" tests
    private static final int ONE_FAIL_OUT_OF = 2;

    private static final Class[] TESTS_PRIMITIVE = {
            FastUtilMapTest.class,
            GsMutableMapTest.class,
            HftcMutableMapTest.class, //+
            HppcMapTest.class,
            TroveMapTest.class, //+
    };
    private static final Class[] TESTS_WRAPPER = {
            FastUtilObjMapTest.class,
            HftcMutableObjTest.class, //+
            HftcNotNullKeyObjTest.class,
            HppcObjMapTest.class,
            GsObjMapTest.class,
            JdkMapTest.class,  //+
            JdkMapTestDifferentCapacity.class,  //+
            TroveObjMapTest.class,   //+
    };
    private static final Class[] TESTS_PRIMITIVE_WRAPPER = {
            FastUtilIntObjectMapTest.class,
            GsIntObjectMapTest.class,
            HftcIntObjectMapTest.class,   //+
            HppcIntObjectMapTest.class,
            TroveIntObjectMapTest.class,   //+
    };
    private static final Class[] TESTS_WRAPPER_PRIMITIVE = {
            FastUtilObjectIntMapTest.class,
            GsObjectIntMapTest.class,
            HftcObjectIntMapTest.class,   //+
            HppcObjectIntMapTest.class,
            TroveObjectIntMapTest.class,   //+
    };

    private static final Class[] TESTS_IDENTITY = {
            FastUtilRef2ObjectMapTest.class,
            GsIdentityMapTest.class,
            HftcIdentityMapTest.class,
            HppcIdentityMapTest.class,
            JDKIdentityMapTest.class,
            TroveIdentityMapTest.class,
    };

    public static void main(String[] args) throws RunnerException, InstantiationException, IllegalAccessException
    {
        final LinkedHashMap<String, String> res = new LinkedHashMap<>(3);
        res.put( "get", runTestSet( "get" ) );
        res.put( "put", runTestSet( "put" ) );
        res.put( "remove", runTestSet( "remove" ) );

        for ( final Map.Entry<String, String> entry : res.entrySet() )
        {
            System.out.println( "Results for '" + entry.getKey() + "':" );
            System.out.println( entry.getValue() );
            System.out.println();
        }
    }

    private static String runTestSet(final String testSetName) throws RunnerException, InstantiationException, IllegalAccessException
    {
        final List<Class> tests = new ArrayList<>();
        tests.addAll( Arrays.asList( TESTS_PRIMITIVE ) );
        tests.addAll( Arrays.asList( TESTS_WRAPPER ) );
        tests.addAll( Arrays.asList( TESTS_PRIMITIVE_WRAPPER ) );
        tests.addAll( Arrays.asList( TESTS_WRAPPER_PRIMITIVE ) );
        tests.addAll( Arrays.asList( TESTS_IDENTITY ) );

        //first level: test class, second level - map size
        final Map<String, Map<Integer, String>> results = new HashMap<>();

        if ( BILLION_TEST )
        { //JMH does not feel well on these sizes
            testBillion( tests );
            return "";
        }

        //pick map size first - we need to generate a set of keys to be used in all tests
        for (final int mapSize : MAP_SIZES) {
            //run tests one after another
            for ( final Class testClass : tests ) {
                Options opt = new OptionsBuilder()
                        .include(".*" + MapTestRunner.class.getSimpleName() + ".*")
                        .forks(1)
                        .mode(Mode.SingleShotTime)
                        .warmupBatchSize(TOTAL_SIZE / mapSize)
                        .warmupIterations(10)
                        .measurementBatchSize(TOTAL_SIZE / mapSize)
                        .measurementIterations(10)
                        .jvmArgsAppend("-Xmx30G")
                        .param("m_mapSize", Integer.toString(mapSize))
                        .param("m_className", testClass.getCanonicalName())
                        .param("m_testType", testSetName)
                        //.verbosity(VerboseMode.SILENT)
                        .build();

                Collection<RunResult> res = new Runner(opt).run();
                for ( RunResult rr : res )
                {
                    System.out.println( testClass.getCanonicalName() + " (" + mapSize + ") = " + rr.getAggregatedResult().getPrimaryResult().getScore() );
                    Map<Integer, String> forClass = results.get( testClass.getCanonicalName() );
                    if ( forClass == null )
                        results.put( testClass.getCanonicalName(), forClass = new HashMap<>( 4 ) );
                    forClass.put(mapSize, Integer.toString((int) rr.getAggregatedResult().getPrimaryResult().getScore()) );
                }
                if ( res.isEmpty() ) {
                    Map<Integer, String> forClass = results.get( testClass.getCanonicalName() );
                    if ( forClass == null )
                        results.put( testClass.getCanonicalName(), forClass = new HashMap<>( 4 ) );
                    forClass.put(mapSize, "-1");
                }
            }
        }
        final String res = formatResults(results, MAP_SIZES, tests);
        System.out.println( "Results for test type = " + testSetName + ":\n" + res);

        return res;
    }

    private static void testBillion( final List<Class> tests ) throws IllegalAccessException, InstantiationException {
        final int mapSize = 1000 * 1000 * 1000;
        for ( final Class klass : tests )
        {
            System.gc();
            final IMapTest obj = (IMapTest) klass.newInstance();
            System.out.println( "Prior to setup for " + klass.getName() );
            obj.setup(KeyGenerator.getKeys(mapSize), FILL_FACTOR, ONE_FAIL_OUT_OF);
            System.out.println( "After setup for " + klass.getName() );
            final long start = System.currentTimeMillis();
            obj.test();
            final long time = System.currentTimeMillis() - start;
            System.out.println( klass.getName() + " : time = " + ( time / 1000.0 ) + " sec");
        }
    }

    private static String formatResults( final Map<String, Map<Integer, String>> results, final int[] mapSizes, final List<Class> tests )
    {
        final StringBuilder sb = new StringBuilder( 2048 );
        //format results
        //first line - map sizes, should be sorted
        for ( final int size : mapSizes )
            sb.append( "," ).append( size );
        sb.append( "\n" );
        //following lines - tests in the definition order
        for ( final Class test : tests )
        {
            final Map<Integer, String> res = results.get( test.getCanonicalName() );
            sb.append( test.getName() );
            for ( final int size : mapSizes )
                sb.append( ",\"" ).append( res.get( size ) ).append( "\"" );
            sb.append( "\n" );
        }
        return sb.toString();
    }

    private static class KeyGenerator
    {
        public static int s_mapSize;
        public static int[] s_keys;

        public static int[] getKeys( final int mapSize )
        {
            if ( mapSize == s_mapSize )
                return s_keys;
            s_mapSize = mapSize;
            s_keys = null; //should be done separately so we don't keep 2 arrays in memory
            s_keys = new int[ mapSize ];
            final Random r = new Random( 1234 );
            for ( int i = 0; i < mapSize; ++i )
                s_keys[ i ] = r.nextInt();
            return s_keys;
        }
    }


    @Param("1")
    public int m_mapSize;

    @Param("dummy")
    public String m_className;

    @Param( {"get", "put", "remove"} )
    public String m_testType;

    private IMapTest m_impl;

    @Setup
    public void setup()
    {
        try {
            final ITestSet testSet = (ITestSet) Class.forName( m_className ).newInstance();
            switch ( m_testType )
            {
                case "get":
                    m_impl = testSet.getTest();
                    break;
                case "put":
                    m_impl = testSet.putTest();
                    break;
                case "remove":
                    m_impl = testSet.removeTest();
                    break;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //share the same keys for all tests with the same map size
        m_impl.setup( KeyGenerator.getKeys( m_mapSize ), FILL_FACTOR, ONE_FAIL_OUT_OF );
    }

    @Benchmark
    public int testRandom()
    {
        return m_impl.test();
    }


}
