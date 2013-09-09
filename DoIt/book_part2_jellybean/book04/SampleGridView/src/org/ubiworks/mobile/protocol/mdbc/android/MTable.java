package org.ubiworks.mobile.protocol.mdbc.android;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Table Structure for MDBC
 * 
 * @author mike
 * @date 2005. 03. 10
 */
public class MTable {

	/**
	 * The Name of this table
	 */
	public String name;

	/**
	 * Count of Columns of this table
	 */
	public int countColumn;

	/**
	 * Column Array
	 */
	public MColumn[] columns;

	/**
	 * Records Array
	 */
	public MRecord[] records;

	/** Count of the elements */
	protected int elementCount;

	/** Capacity */
	protected int capacityIncrement;

	/**
	 * Create a new MTable object
	 */
	public MTable() {
		this("", 10);
	}

	/**
	 * Create a new MTable object
	 */
	public MTable(String in_name) {
		this(in_name, 10);
	}

	/**
	 * Create a new MTable object
	 */
	public MTable(String in_name, int initial) {
		name = in_name;

		records = new MRecord[initial];
		capacityIncrement = 0;
	}

	/**
	 * Add Record
	 */
	public final void add(MRecord rec_obj) {
		ensureCapacity(elementCount + 1);
		records[elementCount++] = rec_obj;
	}

	/**
	 * get the capacity of this record container
	 */
	public final int capacity() {
		return records.length;
	}

	/**
	 * get Record
	 */
	public final MRecord get(int index) throws MTableException {
		if (index >= elementCount) {
			throw new MTableException(
					"ArrayIndexOutOfBoundsException in records.");
		}

		return records[index];
	}

	/**
	 * get all the records
	 */
	public final MRecordIterator iterator() {
		return new MRecordIteratorImpl(this);
	}

	/**
	 * ensure capacity
	 */
	public void ensureCapacity(int i) {
		int j = records.length;
		if (i > j) {
			MRecord rec_object[] = records;
			int k = capacityIncrement <= 0 ? j * 2 : j + capacityIncrement;
			if (k < i)
				k = i;
			records = new MRecord[k];
			System.arraycopy(rec_object, 0, records, 0, elementCount);
		}
	}

	/**
	 * returns true if there is no element
	 */
	public boolean isEmpty() {
		return elementCount == 0;
	}

	/**
	 * remove all the shape object
	 */
	public final void removeAll() {
		for (int i = 0; i < elementCount; i++)
			records[i] = null;

		elementCount = 0;
	}

	/**
	 * remove object at the input index
	 */
	public final void remove(int index) throws MTableException {
		if (index >= elementCount)
			throw new MTableException(
					"ArrayIndexOutOfBoundsException in removing a record.");

		int j = elementCount - index - 1;
		if (j > 0) {
			System.arraycopy(records, index + 1, records, index, j);
		}

		elementCount--;
		records[elementCount] = null;
	}

	/**
	 * set a MRecord object
	 */
	public void set(MRecord rec_object, int index) throws MTableException {
		if (index >= elementCount) {
			throw new MTableException(
					"ArrayIndexOutOfBoundsException in setting a record.");
		} else {
			records[index] = rec_object;
			return;
		}
	}

	/**
	 * get the number of objects
	 */
	public final int count() {
		return elementCount;
	}

	/**
	 * trim to size
	 */
	public final void trimToSize() {
		int i = records.length;
		if (elementCount < i) {
			MRecord rec_object[] = records;
			records = new MRecord[elementCount];
			System.arraycopy(rec_object, 0, records, 0, elementCount);
		}
	}

	/**
	 * Add a Column
	 */
	public void addColumn(MColumn in_column) {
		MColumn[] newColumns = new MColumn[countColumn + 1];

		if (countColumn > 0) {
			System.arraycopy(columns, 0, newColumns, 0, countColumn);
		}

		newColumns[countColumn] = in_column;

		columns = newColumns;
		countColumn++;
	}

	/**
	 * Add a Column with name, type and length
	 */
	public void addColumn(String in_name, int in_type, int in_length) {
		addColumn(new MColumn(in_name, in_type, in_length));
	}

	/**
	 * Add a Column with name, type and length and scale
	 */
	public void addColumn(String in_name, int in_type, int in_length,
			int in_scale) {
		addColumn(new MColumn(in_name, in_type, in_length, in_scale));
	}

	/**
	 * Add a Column with name and type
	 */
	public void addColumn(String in_name, int in_type) {
		addColumn(new MColumn(in_name, in_type));
	}

	/**
	 * Remove a Column
	 */
	public void removeColumn(int index) throws MTableException {
		if (index < 0 || index >= countColumn) {
			throw new MTableException("Input index is out of bounds...");
		}

		MColumn[] newColumns = new MColumn[countColumn - 1];
		if (index > 0) {
			System.arraycopy(columns, 0, newColumns, 0, index);
		}

		if (index < countColumn - 1) {
			System.arraycopy(columns, index + 1, newColumns, 0, countColumn
					- index - 1);
		}

		columns = newColumns;
		countColumn--;

		// remove cell according to the removed column from records
	}

	/**
	 * make a byte array from all columns in this table
	 */
	public byte[] makeColumnBytes() {
		return toColumnBytes();
	}

	/**
	 * make a byte array from all columns in this table
	 */
	public byte[] toColumnBytes() {
		byte[][] col_array = new byte[columns.length][];
		int col_length = 0;

		for (int i = 0; i < columns.length; i++) {
			col_array[i] = columns[i].toBytes();
			col_length += col_array[i].length;
		}

		byte[] output = new byte[col_length + columns.length * 4];
		int sindex = 0;
		byte[] sep_bytes = null;
		for (int i = 0; i < columns.length; i++) {
			sep_bytes = TypeProcessor.toBytes(col_array[i].length);
			System.arraycopy(sep_bytes, 0, output, sindex, 4);
			sindex = sindex + 4;
			System.arraycopy(col_array[i], 0, output, sindex,
					col_array[i].length);
			sindex += col_array[i].length;
		}

		return output;
	}

	/** make a byte array from all records in this table */
	public byte[] getRecordBytes() {
		return null;
	}

	/**
	 * make column information from the input bytes
	 */
	public MColumn[] makeColumn(byte[] input) {
		return fromColumnBytes(input);
	}

	/**
	 * read in column information from the input bytes
	 */
	public MColumn[] fromColumnBytes(byte[] input) {
		MColumn[] output = new MColumn[countColumn];

		int oindex = 0;
		byte[] nbytes = null;

		byte[] numbytes = new byte[4];
		int numint = 0;

		for (int i = 0; i < countColumn; i++) {
			System.arraycopy(input, oindex, numbytes, 0, 4);
			oindex += 4;
			numint = TypeProcessor.getInt(numbytes);

			nbytes = new byte[numint];
			System.arraycopy(input, oindex, nbytes, 0, numint);
			output[i] = new MColumn(nbytes);
			oindex = oindex + numint;
		}

		return output;
	}

	/** make a byte array from this table information(header and column info) */
	public byte[] makeTableBytes() {
		return toTableBytes();
	}

	/** make a byte array from this table information(header and column info) */
	public byte[] toTableBytes() {
		byte[] name_bytes = null;
		try {
			name_bytes = TypeProcessor.writeUTF(name);
		} catch (Exception ex) {
		}

		byte[] count_bytes = TypeProcessor.toBytes(countColumn);
		byte[] col_bytes = toColumnBytes();
		byte[] sep_bytes = null;

		byte[] output = new byte[name_bytes.length + count_bytes.length
				+ col_bytes.length + 12];
		int sindex = 0;
		sep_bytes = TypeProcessor.toBytes(name_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(name_bytes, 0, output, sindex, name_bytes.length);
		sindex += name_bytes.length;
		sep_bytes = TypeProcessor.toBytes(count_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(count_bytes, 0, output, sindex, count_bytes.length);
		sindex += count_bytes.length;
		sep_bytes = TypeProcessor.toBytes(col_bytes.length);
		System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
		sindex = sindex + 4;
		System.arraycopy(col_bytes, 0, output, sindex, col_bytes.length);

		return output;
	}

	/**
	 * read table information(header and column info) from byte array for this
	 * object
	 */
	public int makeTable(byte[] input) {
		return fromTableBytes(input);
	}

	/**
	 * read table information(header and column info) from byte array for this
	 * object
	 */
	public int fromTableBytes(byte[] input) {
		int oindex = 0;
		byte[] nbytes = null;

		// Table Name
		int numint = 0;
		numint = readSize(input, oindex);
		oindex += 4;

		String tabname = "";
		nbytes = readContents(input, oindex, numint);
		oindex = oindex + numint;

		try {
			name = TypeProcessor.readUTF(nbytes, nbytes.length);
			System.out.println("DEBUG - Table Name : " + name);
		} catch (Exception ex) {
		}

		// Tab Count
		numint = readSize(input, oindex);
		oindex += 4;

		nbytes = readContents(input, oindex, numint);
		oindex = oindex + numint;

		countColumn = TypeProcessor.getInt(nbytes);

		// Columns
		numint = readSize(input, oindex);
		oindex += 4;

		nbytes = readContents(input, oindex, numint);
		oindex += numint;

		columns = fromColumnBytes(nbytes);

		return oindex;
	}

	/** read in the size of bytes */
	public int readSize(byte[] input, int offset) {
		// MIKE 050603
		if (input.length <= offset) {
			return -1;
		}

		byte[] numbytes = new byte[4];
		System.arraycopy(input, offset, numbytes, 0, 4);

		return TypeProcessor.getInt(numbytes);
	}

	/** read in the size of contents */
	public byte[] readContents(byte[] input, int offset, int numint) {
		byte[] tbytes = new byte[numint];
		System.arraycopy(input, offset, tbytes, 0, numint);

		return tbytes;
	}

	/**
	 * make a byte array from designated records
	 */
	public byte[] makeRecordBytes(int index) throws MTableException {
		return toRecordBytes(index);
	}

	/**
	 * Make a byte array from the designated records
	 */
	public byte[] toRecordBytes(int index) throws MTableException {
		MRecord record = (MRecord) records[index];

		byte[][] result = new byte[countColumn][];
		int tot_length = 0;
		Object outobj = null;
		for (int i = 0; i < countColumn; i++) {
			// System.out.println("#" + index + " : " + i);
			switch (columns[i].type) {
			case MType.BYTE:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor.toBytes(((java.lang.Byte) outobj)
							.byteValue());
				}

				tot_length += result[i].length;
				break;
			case MType.BOOLEAN:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((java.lang.Boolean) outobj)
									.booleanValue());
				}

				tot_length += result[i].length;
				break;
			case MType.CHAR:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					try {
						result[i] = TypeProcessor.writeUTF((String) outobj);
					} catch (Exception ex) {
					}
				}

				tot_length += result[i].length;
				break;
			case MType.SHORT:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((java.lang.Short) outobj).shortValue());
				}

				tot_length += result[i].length;
				break;
			case MType.INTEGER:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((java.lang.Integer) outobj).intValue());
				}

				tot_length += result[i].length;
				break;
			case MType.LONG:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor.toBytes(((java.lang.Long) outobj)
							.longValue());
				}

				tot_length += result[i].length;
				break;
			case MType.DOUBLE:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((java.lang.Double) outobj).doubleValue());
				}

				tot_length += result[i].length;
				break;
			case MType.NUMERIC:
				outobj = record.getCell(i);
				// System.out.println("DEBUG : " + outobj.getClass().getName() +
				// " : " + outobj);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					try {
						result[i] = TypeProcessor.writeUTF((String) outobj);
					} catch (Exception ex) {
					}
				}
				tot_length += result[i].length;
				break;
			case MType.STRING:
				outobj = record.getCell(i);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					try {
						result[i] = TypeProcessor.writeUTF((String) outobj);
					} catch (Exception ex) {
					}
				}
				tot_length += result[i].length;
				break;
			case MType.DATE:
				outobj = record.getCell(i);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((Date) record.getCell(i)).getTime());
				}
				tot_length += result[i].length;
				break;
			case MType.TIME:
				outobj = record.getCell(i);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor
							.toBytes(((Time) record.getCell(i)).getTime());
				}
				tot_length += result[i].length;
				break;
			case MType.TIMESTAMP:
				outobj = record.getCell(i);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					result[i] = TypeProcessor.toBytes(((Timestamp) record
							.getCell(i)).getTime());
				}
				tot_length += result[i].length;
				break;
			case MType.NULL: // 255 ASCII
				result[i] = TypeProcessor.toBytes((byte) 0xFF);
				tot_length += result[i].length;
				break;
			default:
				outobj = record.getCell(i);
				if (checkNull(outobj)) {
					result[i] = TypeProcessor.toBytes((byte) 0xFF);
				} else {
					try {
						result[i] = TypeProcessor.writeUTF((String) outobj);
					} catch (Exception ex) {
					}
				}
				tot_length += result[i].length;
				break;

			}
		}

		byte[] output = new byte[tot_length + countColumn * 4];
		int sindex = 0;
		byte[] sep_bytes = null;

		for (int i = 0; i < countColumn; i++) {
			sep_bytes = TypeProcessor.toBytes(result[i].length);
			System.arraycopy(sep_bytes, 0, output, sindex, sep_bytes.length);
			sindex = sindex + 4;
			System.arraycopy(result[i], 0, output, sindex, result[i].length);
			sindex += result[i].length;

		}

		return output;
	}

	/**
	 * Check the input object is Null or not Null -> 0xFF (byte)
	 */
	public boolean checkNull(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof java.lang.Byte) {
			byte outval = ((Byte) obj).byteValue();
			if (outval == (byte) 0xFF || outval == -1) {
				return true;
			}
		}

		return false;
	}

	/** retrieve column information from byte array */
	public MRecord makeRecord(byte[] input) throws MTableException {
		return fromRecordBytes(input);
	}

	/**
	 * Make a Pseudo Record object
	 */
	public MRecord makePseudo() throws MTableException {

		MRecord output = createRecord();

		for (int i = 0; i < countColumn; i++) {
			switch (columns[i].type) {
			case MType.BYTE:
				output.add(new Byte((byte) 0));
				break;
			case MType.BOOLEAN:
				output.add(new Boolean(false));
				break;
			case MType.CHAR:
				output.add("");
				break;
			case MType.SHORT:
				output.add(new Short((short) 0));
				break;
			case MType.INTEGER:
				output.add(new Integer(0));
				break;
			case MType.LONG:
				output.add(new Long(0L));
				break;
			case MType.DOUBLE:
				output.add(new Double(0.0D));
				break;
			case MType.STRING:
				output.add("");
				break;
			case MType.NUMERIC:
				output.add("0.0");
				break;
			case MType.DATE:
				Date objdate1 = new Date(0L);
				output.add(objdate1);
				break;
			case MType.TIME:
				Time objdate2 = new Time(0L);
				output.add(objdate2);
				break;
			case MType.TIMESTAMP:
				Timestamp objdate3 = new Timestamp(0L);
				output.add(objdate3);
				break;
			case MType.NULL: // Make a VOID Wrapper
				output.add(new Byte((byte) 0xFF));
				break;
			default:
				output.add("");

			}
		}

		return output;
	}

	/**
	 * Make a Record from the input byte array
	 */
	public MRecord fromRecordBytes(byte[] input) throws MTableException {
		if (input == null) {
			throw new MTableException("Input bytes is null.");
		}

		MRecord output = createRecord();

		int oindex = 0;
		byte[] nbytes = null;

		int numint = 0;
		for (int i = 0; i < countColumn; i++) {

			numint = readSize(input, oindex);
			oindex += 4;

			nbytes = readContents(input, oindex, numint);
			oindex = oindex + numint;

			switch (columns[i].type) {
			case MType.BYTE:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					byte outbyte = TypeProcessor.getByte(nbytes);
					output.add(new Byte((byte) outbyte));
				}

				break;
			case MType.BOOLEAN:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					boolean outbool = TypeProcessor.getBoolean(nbytes);
					output.add(new Boolean(outbool));
				}

				break;
			case MType.CHAR:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					try {
						output
								.add(TypeProcessor.readUTF(nbytes,
										nbytes.length));
					} catch (Exception ex) {
					}
				}

				break;
			case MType.SHORT:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					short outshort = TypeProcessor.getShort(nbytes);
					output.add(new Short(outshort));
				}

				break;
			case MType.INTEGER:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					int outint = TypeProcessor.getInt(nbytes);
					output.add(new Integer(outint));
				}

				break;
			case MType.LONG:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					long outlong = TypeProcessor.getLong(nbytes);
					output.add(new Long(outlong));
				}

				break;
			case MType.DOUBLE:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					double outdouble = TypeProcessor.getDouble(nbytes);
					output.add(new Double(outdouble));
				}

				break;
			case MType.STRING:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					try {
						output
								.add(TypeProcessor.readUTF(nbytes,
										nbytes.length));
					} catch (Exception ex) {
					}
				}
				break;
			case MType.NUMERIC:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					try {
						output
								.add(TypeProcessor.readUTF(nbytes,
										nbytes.length));
					} catch (Exception ex) {
					}
				}
				break;
			case MType.DATE:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					long outdate1 = TypeProcessor.getLong(nbytes);
					// org.ubiworks.base.sql.ewe.Date objdate1 = new
					// org.ubiworks.base.sql.ewe.Date(outdate1);
					java.util.Date objdate1 = new java.util.Date();
					objdate1.setTime(outdate1);
					output.add(objdate1);
				}
				break;
			case MType.TIME:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					long outdate2 = TypeProcessor.getLong(nbytes);
					// org.ubiworks.base.sql.ewe.Time objdate2 = new
					// org.ubiworks.base.sql.ewe.Time(outdate2);
					java.util.Date objdate2 = new java.util.Date();
					objdate2.setTime(outdate2);
					output.add(objdate2);
				}
				break;
			case MType.TIMESTAMP:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					long outdate3 = TypeProcessor.getLong(nbytes);
					// org.ubiworks.base.sql.ewe.Timestamp objdate3 = new
					// org.ubiworks.base.sql.ewe.Timestamp(outdate3);
					java.util.Date objdate3 = new java.util.Date();
					objdate3.setTime(outdate3);
					output.add(objdate3);
				}
				break;
			case MType.NULL: // Make a VOID Wrapper
				output.add(new Byte((byte) 0xFF));
				break;
			default:
				if (checkNull(nbytes)) {
					// System.out.println("NULL>");
					output.add(new Byte((byte) 0xFF));
				} else {
					try {
						output
								.add(TypeProcessor.readUTF(nbytes,
										nbytes.length));
					} catch (Exception ex) {
					}
				}
			}

		}

		return output;
	}

	public boolean checkNull(byte[] inBytes) {
		if (inBytes == null) {
			return true;
		}

		if (inBytes.length == 1) {
			byte outbyte = TypeProcessor.getByte(inBytes);
			if (outbyte == (byte) 0xFF || outbyte == -1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Create a new Record object
	 */
	public MRecord createRecord() throws MTableException {
		if (countColumn == 0) {
			throw new MTableException("No Column found.");
		}

		return new MRecord(this);
	}

	/**
	 * Get Cell Value with the row and column index
	 */
	public Object getCell(int rowindex, int colindex) throws MTableException {
		if (rowindex < 0 || rowindex > count()) {
			throw new MTableException("Input row index is out of bounds...");
		}

		if (colindex < 0 || colindex > countColumn) {
			throw new MTableException("Input column index is out of bounds...");
		}

		MRecord obj = records[rowindex];

		return obj.getCell(colindex);
	}

	/**
	 * set MTable from All Bytes
	 */
	public void setTable(byte[] input) throws MTableException {

		int oindex = 0;
		byte[] nbytes = null;

		// Table Name
		int numint = 0;
		numint = readSize(input, oindex);
		oindex += 4;

		nbytes = readContents(input, oindex, numint);
		oindex = oindex + numint;

		try {
			this.name = TypeProcessor.readUTF(nbytes, nbytes.length);
			System.out.println("DEBUG - Table Name : " + this.name);
		} catch (Exception ex) {
		}

		// Column Count
		numint = readSize(input, oindex);
		oindex += 4;

		nbytes = readContents(input, oindex, numint);
		oindex = oindex + numint;

		countColumn = TypeProcessor.getInt(nbytes);

		// Columns
		numint = readSize(input, oindex);
		oindex += 4;

		nbytes = readContents(input, oindex, numint);
		oindex = oindex + numint;

		byte[] numbytes = new byte[4];

		for (int i = 0; i < countColumn; i++) {
			System.arraycopy(input, oindex, numbytes, 0, 4);
			oindex += 4;
			numint = TypeProcessor.getInt(numbytes);

			nbytes = new byte[numint];
			System.arraycopy(input, oindex, nbytes, 0, numint);
			columns[i] = new MColumn(nbytes);
			oindex = oindex + numint;
		}

	}

	/**
	 * Convert a MTable object from the input byte array
	 */
	public void makeMTable(byte[] input) throws MTableException {

		int oindex = 0;
		byte[] nbytes = null;
		int numint = 0;

		oindex = fromTableBytes(input);

		// Record
		int countRecord = readSize(input, oindex);
		if (countRecord == -1) {
			System.out.println("DEBUG - No Record.");
		} else {
			// new MessageBox("DEBUG", "Record Count : " + countRecord,
			// 0).execute();
		}

		oindex += 4;
		MRecord aRecord;
		for (int i = 0; oindex + 4 < input.length; i++) {
			numint = readSize(input, oindex);
			oindex += 4;

			nbytes = readContents(input, oindex, numint);
			oindex += numint;

			aRecord = fromRecordBytes(nbytes);
			add(aRecord);
		}

	}

}

/**
 * MRecord Iterator Implementation
 * 
 * Implementation class for the ShapeIterator
 * 
 * @author Mike J. Jung
 * @date 1999. 8. 1.
 */

class MRecordIteratorImpl implements MRecordIterator {

	MTable myTable;

	int index;

	/** Constructor */
	MRecordIteratorImpl(MTable inTable) {
		myTable = inTable;
		index = 0;
	}

	/** Returns true if the next object exists */
	public boolean hasNext() {
		return index < myTable.elementCount;
	}

	/** Returns the next object */
	public MRecord next() {
		synchronized (myTable) {
			if (index < myTable.elementCount) {
				MRecord rec_object = myTable.records[index++];
				return rec_object;
			}
		}
		return null;
	}

}