package cn.newcapec.framework.core.dao.db;

import cn.newcapec.framework.core.dao.db.UUIDGenerator;

import java.net.InetAddress;

/**
 * UUID.HEX生成器
 * 
 */
@SuppressWarnings("all")
public class UUIDHexGenerator implements UUIDGenerator {
	private String sep = "";
	private static final int IP;
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
	private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	public static UUIDHexGenerator getInstance() {
		return uuidgen;
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	protected int getJVM() {
		return JVM;
	}

	protected synchronized short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}

	protected int getIP() {
		return IP;
	}

	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}

	public static void main(String[] str) {
		UUIDHexGenerator id = new UUIDHexGenerator();
		for (int i = 0; i <= 100; i++) {
			new Thread(){
				public void run() {
					for (int i = 0; i <= 100; i++) {
						System.out.println(Thread.currentThread().getId() + "-"+i+":"+ UUIDHexGenerator.getInstance().generate());
					}
				};
			}.start();
		}
		
	}
}