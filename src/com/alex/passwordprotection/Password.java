package com.alex.passwordprotection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Password {
	private static final String TAG = "Password";
	public static final int ROW = 5;
	public static final int COLUMN = 6;
	public static final String[] ROW_ID = { "A", "B", "C", "D", "E" };
	public static final String[] COLUMN_ID = { "1", "2", "3", "4", "5", "6" };
	/**
	 * The number array of the password protection card.
	 */
	private String[][] mPassword = new String[ROW][COLUMN];
	/**
	 * The account of the password
	 */
	private String mAccount;

	private File mPasswordFile;

	public Password(File file) {
		mPasswordFile = file;
	}

	public int loadPassword() {
		if (mPasswordFile == null) {
			Log.e(TAG, "Password file is null!");
			return -1;
		}

		Log.d(TAG, "load password file: " + mPasswordFile.getName());
		if (!mPasswordFile.exists()) {
			Log.e(TAG, "load password file error: file is not exist."
					+ mPasswordFile.getAbsolutePath());
			return -1;
		}
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(mPasswordFile));
			String line = null;
			int count = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (count == 0) {
					mAccount = line;
				} else {
					parsePassword(line, count - 1);
				}
				count++;
			}

			for (int i = 0; i < ROW; i++) {
				Log.d(TAG, "row " + i + ": " + Arrays.toString(mPassword[i]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	private int parsePassword(String line, int row) {
		String[] passwordsOneRow = line.split(" ");
		if (passwordsOneRow == null || passwordsOneRow.length != COLUMN) {
			Log.e(TAG, "password file formate error, line = " + line);
			return -1;
		}
		mPassword[row] = passwordsOneRow;
		return 0;
	}

	public String getPassword(int row, int column) {
		if (mPassword.length > 0) {
			return mPassword[row][column];
		} else {
			return "";
		}
	}

	public String getAccount() {
		return mAccount;
	}
}
