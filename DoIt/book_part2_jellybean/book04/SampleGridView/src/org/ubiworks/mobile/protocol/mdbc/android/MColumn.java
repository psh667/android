package org.ubiworks.mobile.protocol.mdbc.android;


/**
 * Column Data Structure for MDBC
 *
 * @author mike
 * @date 2005. 03. 10
 */

public class MColumn {

	/**
	 * The Name of this column
	 */
	public String name;
	
	/**
	 * The Type of this column
	 */
	public int type;
	
	/**
	 * The Length of this column
	 */
	public int length;

	/**
	 * The Length of this column
	 */
	public int scale;


	/** 
	 * initialize with input bytes containing column info 
	 */
	public MColumn(byte[] in_bytes) {
		fromBytes(in_bytes);
	}

	/** 
	 * initialize with the name and type
	 */
	public MColumn(String in_name, int in_type) {
		name = in_name;
		type = in_type;
	}

	/** 
	 * initialize with the name, type and length
	 */
	public MColumn(String in_name, int in_type, int in_length) {
		name = in_name;
		type = in_type;
		length = in_length;
	}
 
	/** 
	 * initialize with the name, type and length
	 */
	public MColumn(String in_name, int in_type, int in_length, int in_scale) {
		name = in_name;
		type = in_type;
		length = in_length;
		scale = in_scale;
	}
 

	
	/**
	 * make a byte array from this column information 
	 */
	public byte[] makeBytes() {
		return toBytes();
	}

	/** make a byte array from this column information */
	public byte[] toBytes() {
		
		// convert to bytes
		
		byte[] name_bytes = null;
		try {
			name_bytes = TypeProcessor.writeUTF(name);
		} catch(Exception ex) {}
		
		byte[] type_bytes = TypeProcessor.toBytes(type);
		byte[] length_bytes = TypeProcessor.toBytes(length);
		byte[] scale_bytes = TypeProcessor.toBytes(scale);
		byte[] sep_bytes = null;
		
		// make as bytes
		
		byte[] output = new byte[name_bytes.length+type_bytes.length+length_bytes.length+16];
		int sindex = 0;
		sep_bytes = TypeProcessor.toBytes(name_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(name_bytes, 0, output, sindex, name_bytes.length);
		sindex += name_bytes.length;
		sep_bytes = TypeProcessor.toBytes(type_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(type_bytes, 0, output, sindex, type_bytes.length);
		sindex += type_bytes.length;
		sep_bytes = TypeProcessor.toBytes(length_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(length_bytes, 0, output, sindex, length_bytes.length);
		sep_bytes = TypeProcessor.toBytes(scale_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(scale_bytes, 0, output, sindex, scale_bytes.length);
		
		return output;
	}

	/**
	 * retrieve column information from byte array 
	 */
	public void make(byte[] input) {	
		fromBytes(input);
	}

	/**
	 * retrieve column information from byte array 
	 */
	public void fromBytes(byte[] input) {		
		
		int oindex = 0;		
		
		byte[] numbytes = new byte[4];
		int numint = 0;
		
		
		// name part
		
		System.arraycopy(input, oindex, numbytes, 0, 4);
		oindex += 4;
		numint = TypeProcessor.getInt(numbytes);
		
		byte[] tbytes = new byte[numint];
		System.arraycopy(input, oindex, tbytes, 0, numint);						
		
		try {
			name = TypeProcessor.readUTF(tbytes, tbytes.length);	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		oindex = oindex + numint;
					
		// type part	
		System.arraycopy(input, oindex, numbytes, 0, 4);
		oindex += 4;
		numint = TypeProcessor.getInt(numbytes);
			
		tbytes = new byte[numint];
		System.arraycopy(input, oindex, tbytes, 0, numint);						
		type = TypeProcessor.getInt(tbytes);	
		oindex = oindex + numint;		

		// length part
		System.arraycopy(input, oindex, numbytes, 0, 4);
		oindex += 4;
		numint = TypeProcessor.getInt(numbytes);
			
		tbytes = new byte[numint];
		System.arraycopy(input, oindex, tbytes, 0, numint);						
		length = TypeProcessor.getInt(tbytes);	
		
		// scale part
		System.arraycopy(input, oindex, numbytes, 0, 4);
		oindex += 4;
		numint = TypeProcessor.getInt(numbytes);
			
		tbytes = new byte[numint];
		System.arraycopy(input, oindex, tbytes, 0, numint);						
		scale = TypeProcessor.getInt(tbytes);	
					
					
	}


}