package tests.maptests.object;

import java.util.HashMap;
import java.util.Map;

/**
 * JDK HashMap with the capacity ensuring there is enough space.
 * REVERT PARENT CLASS TO STATIC CLASSES IF THIS TEST IS REMOVED!
 */
public class JdkMapTestDifferentCapacity extends JdkMapTest {
    protected <T, V> Map<T, V> makeMap( final int size, final float fillFactor )
    {
        return new HashMap<>( (int) (size / fillFactor + 1), fillFactor );
    }
}
