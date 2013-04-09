package com.alex.passwordprotection;

public class Log {

	public static void d(String tag, String log) {
		System.out.println("[" + tag + "]" + log);
	}

	public static void e(String tag, String log) {
		System.err.println("[" + tag + "]" + log);
	}
}
