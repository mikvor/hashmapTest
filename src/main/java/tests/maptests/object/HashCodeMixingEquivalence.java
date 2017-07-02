package tests.maptests.object;


import com.koloboke.collect.StatelessEquivalence;

public final class HashCodeMixingEquivalence extends StatelessEquivalence {
    public static final HashCodeMixingEquivalence INSTANCE = new HashCodeMixingEquivalence();

    // copied from net.openhft.koloboke.impl.hash.LHash
    private static final int INT_PHI_MAGIC = -1640531527;
    
    private HashCodeMixingEquivalence() {}
    
    @Override
    public boolean equivalent(Object o1, Object o2) {
        return o1.equals(o2);
    }

    @Override
    public int hash(Object obj) {
        return obj.hashCode() * INT_PHI_MAGIC;
    }
}
