package tests.maptests;

/**
 * Each test class will implement several test interfaces. Each test will be initialized and run independently.
 * Currently there are 2 sets:
 * 1) get - successful and failing
 * 2) put - add and update
 */
public interface ITestSet {
    public IMapTest getTest();
    public IMapTest putTest();
    public IMapTest removeTest();
}
