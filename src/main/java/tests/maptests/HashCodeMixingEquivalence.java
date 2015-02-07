package tests.maptests;

import net.openhft.koloboke.collect.StatelessEquivalence;

import javax.annotation.Nonnull;

public final class HashCodeMixingEquivalence extends StatelessEquivalence {
    public static final HashCodeMixingEquivalence INSTANCE = new HashCodeMixingEquivalence();

    // copied from net.openhft.koloboke.impl.hash.LHash
    private static final int INT_PHI_MAGIC = -1640531527;
    
    private HashCodeMixingEquivalence() {}
    
    @Override
    public boolean equivalent(@Nonnull Object o1, @Nonnull Object o2) {
        return o1.equals(o2);
    }

    @Override
    public int hash(@Nonnull Object obj) {
        return obj.hashCode() * INT_PHI_MAGIC;
    }
}
