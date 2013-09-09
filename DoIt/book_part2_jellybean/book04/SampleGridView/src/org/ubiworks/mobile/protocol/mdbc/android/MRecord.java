package org.ubiworks.mobile.protocol.mdbc.android;

import java.util.Vector;


/**
 * Record Data Structure for MDBC
 *
 * @author mike
 * @date 2005. 03. 10
 */
public class MRecord extends Vector {

	/**
	 * Reference to the MTable
	 */
	public MTable myTable;

	/**
	 * Constructor for this Record
	 */
	public MRecord(MTable inTable) {
		super(inTable.countColumn);
		
		myTable = inTable;
	}
	
	/**
	 * add new object for this record
	 */
	public void addCell(Object obj)
		throws MTableException
	{
		checkSize();
		
		super.add(obj);
	}
	
	
	/**
	 * add new object for this record
	 */
	public void addCell(boolean obj)
		throws MTableException
	{
		checkSize();
				
		super.add(new Boolean(obj));
	}

	/**
	 * add new object for this record
	 */
	public void addCell(byte obj)
		throws MTableException
	{
		checkSize();
		 
		super.add(new Byte(obj));
	}

	/**
	 * add new object for this record
	 */	
	public void addCell(char obj)
		throws MTableException
	{
		checkSize();
		 
		super.add(new Character(obj));
	}

	/**
	 * add new object for this record
	 */
	public void addCell(short obj)
		throws MTableException
	{
		checkSize();
				
		super.add(new Short(obj));
	}

	/**
	 * add new object for this record
	 */
	public void addCell(int obj)
		throws MTableException
	{
		checkSize();
		 
		super.add(new Integer(obj));
	}

	/**
	 * add new object for this record
	 */
	public void addCell(long obj)
		throws MTableException
	{
		checkSize();
		 
		super.add(new Long(obj));
	}

	/**
	 * add new object for this record
	 */
	public void addCell(double obj)
		throws MTableException
	{
		checkSize();
		 
		super.add(new Double(obj));
	}

	/**
	 * set new object for this record
	 */	
	public void setCell(int index, byte obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Byte) {
			set(index, new Byte(obj));
		} else {
			throw new MTableException("Type is not java.lang.Byte.");
		}
		
	}

	/**
	 * set new object for this record
	 */	
	public void setCell(int index, boolean obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Boolean) {
			set(index, new Boolean(obj));
		} else {
			throw new MTableException("Type is not java.lang.Boolean.");
		}
		
	}

	/**
	 * set new object for this record
	 */	
	public void setCell(int index, char obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Character) {
			set(index, new Character(obj));
		} else {
			throw new MTableException("Type is not java.lang.Character.");
		}
		
	}

	/**
	 * set new object for this record
	 */	
	public void setCell(int index, short obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Short) {
			set(index, new Short(obj));
		} else {
			throw new MTableException("Type is not java.lang.Short.");
		}
		
	}
	
	/**
	 * set new object for this record
	 */	
	public void setCell(int index, int obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Integer) {
			set(index, new Integer(obj));
		} else {
			throw new MTableException("Type is not java.lang.Integer.");
		}
		
	}
	
	/**
	 * set new object for this record
	 */	
	public void setCell(int index, long obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Long) {
			set(index, new Long(obj));
		} else {
			throw new MTableException("Type is not java.lang.Long.");
		}
		
	}
	
	/**
	 * set new object for this record
	 */	
	public void setCell(int index, double obj)
		throws MTableException
	{
		checkIndex(index);
		
		Object out_obj = get(index);
		if (out_obj instanceof java.lang.Double) {
			set(index, new Double(obj));
		} else {
			throw new MTableException("Type is not java.lang.Double.");
		}
		
	}
	
	/**
	 * set new object for this record
	 */	
	public void setCell(int index, Object obj)
		throws MTableException
	{
		checkIndex(index);
		
		set(index, obj);		
	}
				
	/**
	 * return new cell object
	 */	
	public Object getCell(int index)
		throws MTableException
	{
		if (index < 0 || index >= myTable.countColumn) {
			throw new MTableException("Input index is out of bounds...");
		}
		
		return super.get(index);
	}

	/**
	 * check size
	 */
	private void checkSize()
		throws MTableException
	{
		if (size() >= myTable.countColumn) {		
			throw new MTableException("Cell Overflow...");		
		}
	}
	
	/**
	 * check index
	 */
	private void checkIndex(int index)
		throws MTableException
	{
		if (index < 0 || index >= myTable.countColumn) {
			throw new MTableException("Input index is out of bounds...");
		}
	}

}