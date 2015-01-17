package tests.maptests;

/**
 * An interface to be implemented by each performance test
 */
public interface IMapTest {
    public void setup( final int[] keys, final float fillFactor, final int oneFailureOutOf );

    /**
     * This test checks the {@code get} operation on a prefilled map.
     * Success of get operations depends on the {@code oneFailureOutOf} parameter provided during setup.
     * As a result, you can check access to both present and absent values in the map.
     * @return Some value based on the values obtained from a map
     */
    public int randomGetTest();
}
