package org.ubiworks.mobile.protocol.mdbc.android;

public class TypeProcessor {

	public static boolean bigEndian = true;
	protected static byte[] b;

	public static byte[] toBytes(byte input) {
		b = new byte[1];
		b[0] = input;
		return b;
	}

	public static byte getByte(byte[] input) {
		return input[0];
	}

	public static int getUnsignedByte(byte[] input) {
		return input[0] & 0xFF;
	}

	public static byte[] toBytes(boolean input) {
		b = new byte[1];
		b[0] = (input ? (byte) 1 : (byte) 0);
		return b;
	}

	public static boolean getBoolean(byte[] input) {
		return input[0] != 0;
	}

	public static byte[] toBytes(int input) {
		b = new byte[4];
		for (int j = 0; j < 4; j++) {
			b[bigEndian ? 3 - j : j] = (byte) (input & 0xFF);
			input >>= 8;
		}
		return b;
	}

	public static int getInt(byte[] input) {
		if (bigEndian)
			return (((input[0] & 0xFF) << 24) | ((input[1] & 0xFF) << 16)
					| ((input[2] & 0xFF) << 8) | (input[3] & 0xFF));

		return (((input[3] & 0xFF) << 24) | ((input[2] & 0xFF) << 16)
				| ((input[1] & 0xFF) << 8) | (input[0] & 0xFF));
	}

	public static byte[] toBytes(short input) {
		b = new byte[2];
		for (int j = 0; j < 2; j++) {
			b[bigEndian ? 1 - j : j] = (byte) (input & 0xFF);
			input >>= 8;
		}
		return b;
	}

	public static int getUnsignedShort(byte[] input) {
		if (bigEndian)
			return (((input[0] & 0xFF) << 8) | (input[1] & 0xFF));

		return (((input[1] & 0xFF) << 8) | (input[0] & 0xFF));
	}

	public static short getShort(byte[] input) {
		return (short) getUnsignedShort(input);
	}

	public static byte[] toBytes(float input) {
		return toBytes(Float.floatToIntBits(input));
	}

	public static float getFloat(byte[] input) {
		return Float.intBitsToFloat(getInt(input));
	}

	public static byte[] toBytes(long input) {
		b = new byte[8];
		if (bigEndian) {

		} else {
			swapBytes(b);
		}

		b[0] = (byte) (input >>> 56);
		b[1] = (byte) (input >>> 48);
		b[2] = (byte) (input >>> 40);
		b[3] = (byte) (input >>> 32);
		b[4] = (byte) (input >>> 24);
		b[5] = (byte) (input >>> 16);
		b[6] = (byte) (input >>> 8);
		b[7] = (byte) (input >>> 0);

		return b;
	}

	public static long getLong(byte[] input) {
		if (bigEndian) {

		} else {
			swapBytes(input);
		}

		return (((long) input[0] << 56) + ((long) (input[1] & 255) << 48)
				+ ((long) (input[2] & 255) << 40)
				+ ((long) (input[3] & 255) << 32)
				+ ((long) (input[4] & 255) << 24) + ((input[5] & 255) << 16)
				+ ((input[6] & 255) << 8) + ((input[7] & 255) << 0));
	}

	public static byte[] toBytes(double input) {
		return toBytes(Double.doubleToLongBits(input));
	}

	public static double getDouble(byte[] input) {
		return Double.longBitsToDouble(getLong(input));
	}

	private static byte[] swapBytes(byte[] input) {
		byte[] bts = new byte[input.length];
		int j = input.length - 1;
		for (int i = 0; i < input.length; i++, j--) {
			bts[i] = input[j];
		}

		return bts;
	}

	public static byte[] toBytes(String s) {
		int len = s != null ? s.length() : 0;
		int len2;
		byte[] lenbyte = null;
		if (len >= 255) {
			len2 = 3;
			lenbyte = new byte[3];
			toBytes((byte) 0xFF);
			toBytes((short) len);
		} else {
			len2 = 1;
			toBytes((byte) len);
		}

		return toFixedString(s, len);
	}

	/** use this for hangul processing */
	public final static String readUTF(byte[] bytearr, int utflen)
			throws Exception {
		int c, char2, char3;
		int count = 0;

		StringBuffer str = new StringBuffer();
		while (count < utflen) {
			c = (int) bytearr[count] & 0xff;
			switch (c >> 4) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				/* 0xxxxxxx */
				count++;
				str.append((char) c);
				break;
			case 12:
			case 13:
				/* 110x xxxx 10xx xxxx */
				count += 2;
				if (count > utflen)
					throw new Exception();
				char2 = (int) bytearr[count - 1];
				if ((char2 & 0xC0) != 0x80)
					throw new Exception();
				str.append((char) (((c & 0x1F) << 6) | (char2 & 0x3F)));
				break;
			case 14:
				/* 1110 xxxx 10xx xxxx 10xx xxxx */
				count += 3;
				if (count > utflen)
					throw new Exception();
				char2 = (int) bytearr[count - 2];
				char3 = (int) bytearr[count - 1];
				if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
					throw new Exception();
				str
						.append((char) (((c & 0x0F) << 12)
								| ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0)));
				break;
			default:
				/* 10xx xxxx, 1111 xxxx */
				throw new Exception();
			}
		}
		// The number of chars produced may be less than utflen
		// return new String(str);
		return str.toString();
	}

	/** use this for hangul processing */
	public final static byte[] writeUTF(String str) throws Exception {
		int strlen = str.length();
		int utflen = 0;
		char[] charr = new char[strlen];
		int c, count = 0;

		str.getChars(0, strlen, charr, 0);

		for (int i = 0; i < strlen; i++) {
			c = charr[i];
			if ((c >= 0x0001) && (c <= 0x007F)) {
				utflen++;
			} else if (c > 0x07FF) {
				utflen += 3;
			} else {
				utflen += 2;
			}
		}

		if (utflen > 65535)
			throw new Exception();

		// byte[] bytearr = new byte[utflen+2];
		// bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
		// bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

		byte[] bytearr = new byte[utflen];

		for (int i = 0; i < strlen; i++) {
			c = charr[i];
			if ((c >= 0x0001) && (c <= 0x007F)) {
				bytearr[count++] = (byte) c;
			} else if (c > 0x07FF) {
				bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
				bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			} else {
				bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
				bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
			}
		}

		return bytearr;
	}

	public static byte[] toFixedString(String s, int length) {
		return toFixedString(s, length, ' ');
	}

	public static byte[] toFixedString(String s, int length, char pad) {
		if (length == 0)
			return null;

		byte[] b = new byte[length];
		int slen = 0;
		if (s != null) {
			char[] c = s.toCharArray();
			slen = c.length;
			for (int i = 0; i < slen && i < length; i++)
				b[i] = (byte) c[i];
		}

		for (int i = slen; i < length; i++)
			b[i] = (byte) pad; // pad the rest

		return b;
	}

	/** linput is 3 length */
	public static String getString(byte[] linput, byte[] cinput) {
		int len = getUnsignedByte(linput);
		if (len == 0xFF) {
			byte[] imsi = new byte[2];
			imsi[0] = linput[1];
			imsi[1] = linput[2];
			len = getShort(imsi);
		}
		return getFixedString(len, cinput);
	}

	public static String getFixedString(int length, byte[] cinput) {
		char[] c = new char[length];

		for (int i = 0; i < length; i++)
			c[i] = (char) b[i];
		return new String(c);
	}

	/** returns index where '|' character occurs with order */
	public static int splitIndex(byte[] input, int order) {
		int count = 0;
		for (int i = 0; i < input.length; i++) {
			if (input[i] == 0X7C) {
				count++;
				if (count == order) {
					return i;
				}
			}
		}

		return -1;
	}

	/** split original bytes with original and split index */
	public static byte[] splitBytes(byte[] input, int oindex, int sindex) {
		byte[] nbytes = new byte[sindex - oindex];
		System.arraycopy(input, oindex, nbytes, 0, sindex - oindex);

		return nbytes;
	}

	/**
	 * Converts all characters in a string to upper case.
	 * 
	 * @param s
	 *            the string to convert
	 * @return an upper case version of the string
	 */
	public static String toUpperCase(String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if (c[i] >= 'a' && c[i] <= 'z')
				c[i] -= 32;

		return new String(c);
	}

	/**
	 * Converts all characters in a string to lower case.
	 * 
	 * @param s
	 *            the string to convert
	 * @return a lower case version of the string
	 */
	public static String toLowerCase(String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if (c[i] >= 'A' && c[i] <= 'Z')
				c[i] += 32;

		return new String(c);
	}

}