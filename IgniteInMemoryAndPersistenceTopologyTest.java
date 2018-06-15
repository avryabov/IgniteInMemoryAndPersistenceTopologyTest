package org.apache.ignite;

import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.testframework.junits.common.GridCommonAbstractTest;

/**
 *
 */
public class IgniteInMemoryAndPersistenceTopologyTest extends GridCommonAbstractTest {
    /** Test nodes count. */
    public static final int NODE_CNT = 3;

    /**
     * Test verifies that NODE_CNT nodes with InMemory mode successful added to topology
     */
    public void testInMemoryMode() throws Exception {
        startInMemoryNodes(NODE_CNT);
        checkTopology(NODE_CNT);
    }


    /**
     * Test verifies that NODE_CNT nodes with Persistence mode successful added to topology
     */
    public void testPersistenceMode() throws Exception {
        startPersistenceNodes(NODE_CNT);
        checkTopology(NODE_CNT);
    }

    /** {@inheritDoc} */
    @Override protected void afterTest() throws Exception {
        stopAllGrids();
        cleanPersistenceDir();
    }

    /**
     *
     */
    private void startInMemoryNode(int idx) throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();
        startGrid(getTestIgniteInstanceName(idx), cfg.setIgniteInstanceName(getTestIgniteInstanceName(idx)), null);
    }

    /**
     *
     */
    private void startInMemoryNodes(int cnt) throws Exception {
        stopAllGrids();
        for (int i = 0; i < cnt; i++)
            startInMemoryNode(i);
    }

    /**
     *
     */
    private void startPersistenceNode(int idx) throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();
        DataStorageConfiguration storageCfg = new DataStorageConfiguration();
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
        cfg.setDataStorageConfiguration(storageCfg);
        startGrid(getTestIgniteInstanceName(idx), cfg.setIgniteInstanceName(getTestIgniteInstanceName(idx)), null);
    }

    /**
     *
     */
    private void startPersistenceNodes(int cnt) throws Exception {
        cleanPersistenceDir();
        stopAllGrids();
        for (int i = 0; i < cnt; i++)
            startPersistenceNode(i);
    }
}
