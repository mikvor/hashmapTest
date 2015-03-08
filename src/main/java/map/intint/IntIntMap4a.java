package map.intint;

/**
 * Same as IntIntMap4, but using interleaving int[] instead of long[]
 */
public class IntIntMap4a  implements IntIntMap
{
    private static final int FREE_KEY = 0;
    private static final int REMOVED_KEY = 1;

    public static final int NO_VALUE = 0;

    /** Keys and values */
    private int[] m_data;

    /** Do we have 'free' key in the map? */
    private boolean m_hasFreeKey;
    /** Value of 'free' key */
    private int m_freeValue;

    /** Do we have 'removed' key in the map? */
    private boolean m_hasRemovedKey;
    /** Value of 'removed' key */
    private int m_removedValue;

    /** Fill factor, must be between (0 and 1) */
    private final float m_fillFactor;
    /** We will resize a map once it reaches this size */
    private int m_threshold;
    /** Current map size */
    private int m_size;

    /** Mask to calculate the original position */
    private int m_mask;
    private int m_mask2;

    public IntIntMap4a( final int size, final float fillFactor )
    {
        if ( fillFactor <= 0 || fillFactor >= 1 )
            throw new IllegalArgumentException( "FillFactor must be in (0, 1)" );
        if ( size <= 0 )
            throw new IllegalArgumentException( "Size must be positive!" );
        final int capacity = Tools.arraySize(size, fillFactor);
        m_mask = capacity - 1;
        m_mask2 = capacity*2 - 1;
        m_fillFactor = fillFactor;

        m_data = new int[capacity * 2];
        m_threshold = (int) (capacity * fillFactor);
    }

    public int get( final int key )
    {
        int ptr = (key & m_mask) << 1;

        if ( key == FREE_KEY )
            return m_hasFreeKey ? m_freeValue : NO_VALUE;

        int k = m_data[ ptr ];

        if ( key == REMOVED_KEY )
            return m_hasRemovedKey ? m_removedValue : NO_VALUE;


        if ( k == FREE_KEY )
            return NO_VALUE;  //end of chain already
        if ( k == key ) //we check FREE and REMOVED prior to this call
            return m_data[ ptr + 1 ];

        while ( true )
        {
            ptr = (ptr + 2) & m_mask2; //that's next index
            k = m_data[ ptr ];
            if ( k == FREE_KEY )
                return NO_VALUE;
            if ( k == key )
                return m_data[ ptr + 1 ];
        }
    }

    public int put( final int key, final int value )
    {
        if ( key == FREE_KEY )
        {
            final int ret = m_freeValue;
            if ( !m_hasFreeKey )
                ++m_size;
            m_hasFreeKey = true;
            m_freeValue = value;
            return ret;
        }
        else if ( key == REMOVED_KEY )
        {
            final int ret = m_removedValue;
            if ( !m_hasRemovedKey )
                ++m_size;
            m_hasRemovedKey = true;
            m_removedValue = value;
            return ret;
        }

        int ptr = ( key & m_mask ) << 1;
        int k = m_data[ptr];
        if ( k == FREE_KEY ) //end of chain already
        {
            m_data[ ptr ] = key;
            m_data[ ptr + 1 ] = value;
            if ( m_size >= m_threshold )
                rehash( m_data.length * 2 ); //size is set inside
            else
                ++m_size;
            return NO_VALUE;
        }
        else if ( k == key ) //we check FREE and REMOVED prior to this call
        {
            final int ret = m_data[ ptr + 1 ];
            m_data[ ptr + 1 ] = value;
            return ret;
        }

        int firstRemoved = -1;
        if ( k == REMOVED_KEY )
            firstRemoved = ptr; //we may find a key later

        while ( true )
        {
            ptr = ( ptr + 2 ) & m_mask2; //that's next index calculation
            k = m_data[ ptr ];
            if ( k == FREE_KEY )
            {
                if ( firstRemoved != -1 )
                    ptr = firstRemoved;
                m_data[ ptr ] = key;
                m_data[ ptr + 1 ] = value;
                if ( m_size >= m_threshold )
                    rehash( m_data.length * 2 ); //size is set inside
                else
                    ++m_size;
                return NO_VALUE;
            }
            else if ( k == key )
            {
                final int ret = m_data[ ptr + 1 ];
                m_data[ ptr + 1 ] = value;
                return ret;
            }
            else if ( k == REMOVED_KEY )
            {
                if ( firstRemoved == -1 )
                    firstRemoved = ptr;
            }
        }
    }

    public int remove( final int key )
    {
        if ( key == FREE_KEY )
        {
            if ( !m_hasFreeKey )
                return NO_VALUE;
            m_hasFreeKey = false;
            --m_size;
            return m_freeValue; //value is not cleaned
        }
        else if ( key == REMOVED_KEY )
        {
            if ( !m_hasRemovedKey )
                return NO_VALUE;
            m_hasRemovedKey = false;
            --m_size;
            return m_removedValue; //value is not cleaned
        }

        int ptr = ( key & m_mask ) << 1;
        int k = m_data[ ptr ];
        if ( k == key ) //we check FREE and REMOVED prior to this call
        {
            //try to reduce the number of removed cells
            if ( m_data[ (ptr + 2) & m_mask2 ] == FREE_KEY )
                m_data[ ptr ] = FREE_KEY;
            else
                m_data[ ptr ] = REMOVED_KEY;
            --m_size;
            return m_data[ ptr + 1 ]; //do not clean the value
        }
        else if ( k == FREE_KEY )
            return NO_VALUE;  //end of chain already
        while ( true )
        {
            ptr = ( ptr + 2 ) & m_mask2; //that's next index calculation
            k = m_data[ ptr ];
            if ( k == key )
            {
                //try to reduce the number of removed cells
                if ( m_data[ (ptr + 2) & m_mask2 ] == FREE_KEY )
                    m_data[ ptr ] = FREE_KEY;
                else
                    m_data[ ptr ] = REMOVED_KEY;
                --m_size;
                return m_data[ ptr + 1 ]; //do not clean the value
            }
            else if ( k == FREE_KEY )
                return NO_VALUE;
        }
    }

    public int size()
    {
        return m_size;
    }

    private void rehash( final int newCapacity )
    {
        m_threshold = (int) (newCapacity/2 * m_fillFactor);
        m_mask = newCapacity/2 - 1;
        m_mask2 = newCapacity - 1;

        final int oldCapacity = m_data.length;
        final int[] oldData = m_data;

        m_data = new int[ newCapacity ];
        m_size = ( m_hasFreeKey ? 1 : 0 ) + ( m_hasRemovedKey ? 1 : 0 );

        for ( int i = 0; i < oldCapacity; i += 2 ) {
            final int oldKey = oldData[ i ];
            if( oldKey != FREE_KEY && oldKey != REMOVED_KEY )
                put( oldKey, oldData[ i + 1 ]);
        }
    }
}

