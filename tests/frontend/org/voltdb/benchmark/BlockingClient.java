/**
 * 
 */
package org.voltdb.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ClientStatusListener;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;
import org.voltdb.client.ProcedureCallback;

/**
 * @author pavlo
 *
 */
public class BlockingClient extends Semaphore implements Client {
    static final Logger LOG = Logger.getLogger(BlockingClient.class);
    
    private static final long serialVersionUID = 1L;
    private final Client inner;
    
    private class BlockingCallback implements ProcedureCallback {
        private final ProcedureCallback inner_callback;
        
        public BlockingCallback(ProcedureCallback inner_callback) {
            assert(inner_callback != null);
            this.inner_callback = inner_callback;
            
            final boolean debug = LOG.isDebugEnabled(); 
            try {
                if (debug) LOG.debug("Trying to acquire procedure invocation lock");
                BlockingClient.this.acquire();
                if (debug) LOG.debug("We got it! Let's get it on!");
            } catch (InterruptedException ex) {
                LOG.fatal("Got interrupted while waiting for lock", ex);
                System.exit(1);
            }
            
        }
        
        @Override
        public void clientCallback(ClientResponse clientResponse) {
            if (LOG.isDebugEnabled()) LOG.debug("BlockingCallback is forwarding the client callback on to inner callback");
            this.inner_callback.clientCallback(clientResponse);
            BlockingClient.this.release();
        }
    }
    
    /**
     * 
     */
    public BlockingClient(Client inner) {
        super(1);
        this.inner = inner;
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#addClientStatusListener(org.voltdb.client.ClientStatusListener)
     */
    @Override
    public void addClientStatusListener(ClientStatusListener listener) {
        this.inner.addClientStatusListener(listener);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#backpressureBarrier()
     */
    @Override
    public void backpressureBarrier() throws InterruptedException {
        this.inner.backpressureBarrier();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#blocking()
     */
    @Override
    public boolean blocking() {
        return this.inner.blocking();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#calculateInvocationSerializedSize(java.lang.String, java.lang.Object[])
     */
    @Override
    public int calculateInvocationSerializedSize(String procName, Object... parameters) {
        return this.inner.calculateInvocationSerializedSize(procName, parameters);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#callProcedure(java.lang.String, java.lang.Object[])
     */
    @Override
    public ClientResponse callProcedure(String procName, Object... parameters) throws IOException,
            NoConnectionsException, ProcCallException {
        return this.inner.callProcedure(procName, parameters);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#callProcedure(org.voltdb.client.ProcedureCallback, java.lang.String, java.lang.Object[])
     */
    @Override
    public boolean callProcedure(ProcedureCallback callback, String procName, Object... parameters) throws IOException,
            NoConnectionsException {
        return this.inner.callProcedure(new BlockingCallback(callback), procName, parameters);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#callProcedure(org.voltdb.client.ProcedureCallback, int, java.lang.String, java.lang.Object[])
     */
    @Override
    public boolean callProcedure(ProcedureCallback callback, int expectedSerializedSize, String procName,
            Object... parameters) throws IOException, NoConnectionsException {
        return this.inner.callProcedure(callback, expectedSerializedSize, procName, parameters);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#close()
     */
    @Override
    public void close() throws InterruptedException {
        this.inner.close();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#configureBlocking(boolean)
     */
    @Override
    public void configureBlocking(boolean blocking) {
        this.inner.configureBlocking(blocking);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#createConnection(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void createConnection(String host, int port, String username, String password) throws UnknownHostException,
            IOException {
        this.inner.createConnection(host, Client.VOLTDB_SERVER_PORT, username, password);
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#drain()
     */
    @Override
    public void drain() throws NoConnectionsException {
        this.inner.drain();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getBuildString()
     */
    @Override
    public String getBuildString() {
        return this.inner.getBuildString();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getIOStats()
     */
    @Override
    public VoltTable getIOStats() {
        return this.inner.getIOStats();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getIOStatsInterval()
     */
    @Override
    public VoltTable getIOStatsInterval() {
        return this.inner.getIOStatsInterval();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getInstanceId()
     */
    @Override
    public Object[] getInstanceId() {
        return this.inner.getInstanceId();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getProcedureStats()
     */
    @Override
    public VoltTable getProcedureStats() {
        return this.inner.getProcedureStats();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#getProcedureStatsInterval()
     */
    @Override
    public VoltTable getProcedureStatsInterval() {
        return this.inner.getProcedureStatsInterval();
    }

    /* (non-Javadoc)
     * @see org.voltdb.client.Client#removeClientStatusListener(org.voltdb.client.ClientStatusListener)
     */
    @Override
    public boolean removeClientStatusListener(ClientStatusListener listener) {
        return this.inner.removeClientStatusListener(listener);
    }

}
