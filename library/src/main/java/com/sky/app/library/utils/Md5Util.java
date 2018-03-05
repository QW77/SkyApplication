package com.sky.app.library.utils;
import java.security.MessageDigest;

/**
 * md5加密
 * @author wjx
 *
 */
public class Md5Util {

	public static String md5(String name) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffers = md.digest(name.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < buffers.length; i++) {
				String s = Integer.toHexString(0xff & buffers[i]);
				if (s.length() == 1) {
					sb.append("0" + s);
				}
				if (s.length() != 1) {
					sb.append(s);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
