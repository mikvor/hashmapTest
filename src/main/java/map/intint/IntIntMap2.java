package map.intint;

/**
 * IntIntMap1 with the only change:
 * Map capacity is now power of 2, array index is calculated with & instead of %
 */
public class IntIntMap2 implements IntIntMap
{
    private static final byte FREE = 0;
    private static final byte USED = 1;
    private static final byte REMOVED = 2;

    public static final int NO_KEY = 0;
    public static final int NO_VALUE = 0;

    /** Keys */
    private int[] m_keys;
    /** Values */
    private int[] m_values;
    /** Occupied? */
    private byte[] m_state;

    /** Fill factor, must be between (0 and 1) */
    private final float m_fillFactor;
    /** We will resize a map once it reaches this size */
    private int m_threshold;
    /** Current map size */
    private int m_size;
    /** Mask to calculate the original position */
    private int m_mask;

    public IntIntMap2( final int size, final float fillFactor )
    {
        if ( fillFactor <= 0 || fillFactor >= 1 )
            throw new IllegalArgumentException( "FillFactor must be in (0, 1)" );
        if ( size <= 0 )
            throw new IllegalArgumentException( "Size must be positive!" );
        final int capacity = Tools.arraySize( size, fillFactor );
        m_mask = capacity - 1;
        m_fillFactor = fillFactor;

        m_keys = new int[capacity];
        m_values = new int[capacity];
        m_state = new byte[capacity];
        m_threshold = (int) (capacity * fillFactor);
    }

    public int get( final int key )
    {
        final int idx = getReadIndex( key );
        return idx != -1 ? m_values[ idx ] : NO_VALUE;
    }

    public int put( final int key, final int value )
    {
        int idx = getPutIndex( key );
        if ( idx < 0 )
        { //no insertion point? Should not happen...
            rehash( m_keys.length * 2 );
            idx = getPutIndex( key );
        }
        final int prev = m_values[ idx ];
        if ( m_state[ idx ] != USED )
        {
            m_keys[ idx ] = key;
            m_values[ idx ] = value;
            m_state[ idx ] = USED;
            ++m_size;
            if ( m_size >= m_threshold )
                rehash( m_keys.length * 2 );
        }
        else //it means used cell with our key
        {
            assert m_keys[ idx ] == key;
            m_values[ idx ] = value;
        }
        return prev;
    }

    public int remove( final int key )
    {
        int idx = getReadIndex( key );
        if ( idx == -1 )
            return NO_VALUE;
        final int res = m_values[ idx ];
        m_keys[ idx ] = NO_KEY;
        m_values[ idx ] = NO_VALUE;
        m_state[ idx ] = REMOVED;
        --m_size;
        return res;
    }

    public int size()
    {
        return m_size;
    }

    private void rehash( final int newCapacity )
    {
        m_threshold = (int) (newCapacity * m_fillFactor);
        m_mask = newCapacity - 1;

        final int oldCapacity = m_keys.length;
        final int[] oldKeys = m_keys;
        final int[] oldValues = m_values;
        final byte[] oldStates = m_state;

        m_keys = new int[ newCapacity ];
        m_values = new int[ newCapacity ];
        m_state = new byte[ newCapacity ];
        m_size = 0;

        for ( int i = oldCapacity; i-- > 0; ) {
            if( oldStates[i] == USED )
                put( oldKeys[ i ], oldValues[ i ] );
        }
    }

    /**
     * Find key position in the map.
     * @param key Key to look for
     * @return Key position or -1 if not found
     */
    private int getReadIndex( final int key )
    {
        int idx = getStartIndex( key );
        if ( m_keys[ idx ] == key && m_state[ idx ] == USED )
            return idx;
        if ( m_keys[ idx ] == FREE ) //end of chain already
            return -1;
        final int startIdx = idx;
        while (( idx = getNextIndex( idx ) ) != startIdx )
        {
            if ( m_state[ idx ] == FREE )
                return -1;
            if ( m_keys[ idx ] == key && m_state[ idx ] == USED )
                return idx;
        }
        return -1;
    }

    /**
     * Find an index of a cell which should be updated by 'put' operation.
     * It can be:
     * 1) a cell with a given key
     * 2) first removed/free cell in the chain
     * @param key Key to look for
     * @return Index of a cell to be updated by a 'put' operation
     */
    private int getPutIndex( final int key )
    {
        final int readIdx = getReadIndex( key );
        if ( readIdx >= 0 )
            return readIdx;
        //key not found, find insertion point
        final int startIdx = getStartIndex( key );
        int idx = startIdx;
        while ( m_state[ idx ] == USED )
        {
            idx = getNextIndex( idx );
            if ( idx == startIdx )
                return -1;
        }
        return idx;
    }


    private int getStartIndex( final int key )
    {
        return key & m_mask;
    }

    private int getNextIndex( final int currentIndex )
    {
        return ( currentIndex + 1 ) & m_mask;
    }
}
