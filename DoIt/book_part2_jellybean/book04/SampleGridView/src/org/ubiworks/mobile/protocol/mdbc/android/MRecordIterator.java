package org.ubiworks.mobile.protocol.mdbc.android;


/**
 * This interface defines method for iterating MRecord Objects in a bag like container
 *
 * @Author : mike
 * @Date : 2004. 1. 2.
 */
public interface MRecordIterator {
    
    /** check if next MRecord Object exists or not */
    public abstract boolean hasNext();

    /** get the next MRecord Object */
    public abstract MRecord next();
    
}