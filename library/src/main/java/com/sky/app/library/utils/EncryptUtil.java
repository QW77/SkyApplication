package com.sky.app.library.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩/解压
 * @author wjx
 *
 */
public class EncryptUtil {
	private static final int BUFFER_SIZE = 1024;
	private static char[] k = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
		'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
		'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
		'Z', '*', '!' };
	
	/**
	 * GZIP 加密压缩
	 * @param str
	 * @return
	 */
	public static String compress(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		try {
			str = encode(str);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(baos);
			gzip.write(str.getBytes("UTF-8"));
			gzip.close();
			byte[] encode = baos.toByteArray();
			baos.flush();
			baos.close();
			return bytesToHexString(encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * GZIP 解密
	 * @param str
	 * @return
	 */
	public static String decompress(String json) {
		try {
			byte[] decode = hexStringToBytes(json);
			ByteArrayInputStream bais = new ByteArrayInputStream(decode);
			GZIPInputStream gzip = new GZIPInputStream(bais);
			byte[] buf = new byte[BUFFER_SIZE];
			int len = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while((len = gzip.read(buf, 0, BUFFER_SIZE)) != -1){
				baos.write(buf, 0, len);
			}
			gzip.close();
			baos.flush();
			decode = baos.toByteArray();
			baos.close();
			return decode(new String(decode, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 十六进制字符串 转换为 byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	private static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 转换 char to byte
	 * @param c
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

	/**
	 * byte[] 转换为 十六进制字符串
	 * 
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();

		if (src == null || src.length <= 0) {
			return null;
		}

		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 编码
	 * @param str
	 * @return
	 */
	private static String encode(String str) {
		if (str == null) {
			return "";
		}
		str = encodeStr(str);
		StringBuffer sb = new StringBuffer();
		char[] array = str.toCharArray();
		sb.append("x");
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i] + k[(i % k.length)]);
			sb.append("_");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("y");
		return sb.toString();
	}

	/**
	 * 解码
	 * @param str
	 * @return
	 */
	private static String decode(String str) {
		if (str == null) {
			return null;
		}
		if ((str.startsWith("x")) && (str.endsWith("y"))) {
			StringBuffer sb = new StringBuffer(str);
			sb.deleteCharAt(0);
			sb.deleteCharAt(sb.length() - 1);
			str = sb.toString();
			String[] strs = str.split("_");
			sb = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				sb.append((char) (Integer.parseInt(strs[i]) - k[(i % k.length)]));
			}
			
			return decodeStr(sb.toString());
		}
		return "";
	}
	
	/**
	 * @param str
	 * @return
	 */
	private static String decodeStr(String str) {
		byte[] debytes = Base64.decode(str);
		try {
			return new String(debytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param str
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String encodeStr(String str) {
		try {
			return Base64.encode(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}