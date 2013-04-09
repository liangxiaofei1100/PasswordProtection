package com.alex.passwordprotection;

import java.io.File;

public class PasswordManager {
	private static final String TAG = "PasswordManager";

	public static final String FOLDER = "files/password/";

	public static File[] getPasswordFiles() {
		File passwordFolder = new File(FOLDER);
		if (!passwordFolder.exists()) {
			Log.e(TAG, "pass word file folder does not exist. folder: "
					+ passwordFolder.getAbsolutePath());
			return null;
		}
		File[] passwordFiles = passwordFolder.listFiles();
		if (passwordFiles != null && passwordFiles.length > 0) {
			// got password file.
			Log.d(TAG, "Got " + passwordFiles.length + " password files.");
		} else {
			Log.e(TAG,
					"No pass word file in folder: "
							+ passwordFolder.getAbsolutePath());
			return null;
		}

		return passwordFiles;
	}
}
