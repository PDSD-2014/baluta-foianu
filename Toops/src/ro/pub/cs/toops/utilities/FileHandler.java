package ro.pub.cs.toops.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;
import android.util.Log;

public class FileHandler {
	private static final String LOG_TAG = "FileHandler";
	public static final String LOCAL_REPO = "scanned_oops";

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	// DIRECTORY_DOCUMENTS works only with API v19
	public static File getDocumentsStorageDir() {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory
				(Environment.DIRECTORY_DOCUMENTS),
				LOCAL_REPO);
		if (!file.mkdirs()) {
			Log.e(LOG_TAG, "Directory not created");
		}
		return file;
	}

	public static void saveOopsToFile(String text) {
		if (!isExternalStorageWritable())
			Log.e(LOG_TAG, "No write permissions!");
		File file = new File(getDocumentsStorageDir(), "qr_"
				+ System.currentTimeMillis());
		try {
			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);
			pw.println(text);
			pw.close();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i(LOG_TAG, "File not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
