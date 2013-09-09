package org.ubiworks.mobile.protocol.mdbc.android;


/**
 * Primitive Data Type for MDBC
 *
 * @author mike
 * @date 2005. 03. 10
 *
 */

public class MType {

	/**
	 * NULL Data Type			
	 */
	public static final int NULL = 0;
	
	/**
	 * BYTE Data Type			-> byte (Java)
	 */
	public static final int BYTE = 1;
	
	/**
	 * BOOLEAN Data Type			-> boolean (Java)
	 */
	public static final int BOOLEAN = 2;
	
	/**
	 * SHORT Data Type (TINYINT, SMALLINT)	-> short (Java)
	 */
	public static final int SHORT = 3;
	
	/**
	 * INTEGER Data Type (TINYINT, SMALLINT)-> int (Java)
	 */
	public static final int INTEGER = 4;
	
	/**
	 * LONG Data Type (BIGINT)		-> long (Java)
	 */
	public static final int LONG = 5;
	
	/**
	 * NUMERIC Data Type (DECIMAL)		-> String (Java)
	 */
	public static final int NUMERIC = 6;
	
        /**
	 * FLOAT Data Type (REAL)	-> float (Java)
	 */
	public static final int FLOAT = 7;
        
	/**
	 * DOUBLE Data Type (FLOAT, REAL)	-> double (Java)
	 */
	public static final int DOUBLE = 8;
	
	/**
	 * CHAR Data Type			-> char (Java)
	 */
	public static final int CHAR = 9;
	
	/**
	 * STRING Data Type (VARCHAR)		-> String (Java)
	 */
	public static final int STRING = 10;
	
	/**
	 * DATE Data Type			-> java.sql.Date (Java) <-> long
	 */
	public static final int DATE = 11;
	
	/**
	 * TIME Data Type			-> java.sql.Time (Java) <-> long
	 */
	public static final int TIME = 12;
	
	/**
	 * TIMESTAMP Data Type			-> java.sql.Timestamp (Java) <-> long
	 */
	public static final int TIMESTAMP = 13;
	
	/**
	 * BINARY Data Type			-> byte array (Java)
	 */
	public static final int BINARY = 14;
	
	/**
	 * UNKNOWN Data Type			-> java.lang.Object (Java)
	 */
	public static final int UNKNOWN = 15;
	
	
}

