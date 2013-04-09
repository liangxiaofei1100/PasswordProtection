package com.alex.passwordprotection;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PasswordPreference {
	private static Preferences mPreferences = Preferences.userRoot().node(
			"PasswordProtection");

	public static void save(File file) {
		mPreferences.put("file", file.getName());
		try {
			mPreferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public static String loadPassordFile() {
		return mPreferences.get("file", null);
	}
}
