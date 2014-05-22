package ro.pub.cs.toops.activities;

import java.util.zip.Inflater;

import ro.pub.cs.toops.libs.IntentIntegrator;
import ro.pub.cs.toops.libs.IntentResult;
import ro.pub.cs.toops.services.QrClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import app.res.R;

public class MainActivity extends Activity {
	public static QrClient rs;
	public final static String EXTRA_MESSAGE = "app.activities.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.scan).setOnClickListener(scanQRCode);
		findViewById(R.id.saved).setOnClickListener(viewSaved);

		String server = PreferenceManager.getDefaultSharedPreferences(this).getString("server", "http://localhost:8080/");
		rs = new QrClient(server);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(this, ConfigActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private final View.OnClickListener scanQRCode = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(
					MainActivity.this);
			integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
		}
	};

	private final View.OnClickListener viewSaved = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String reply = "";
			try {
				reply = rs.listQr();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Reply is a list of id-code pairs in json format currently.
			// Should be parsed by QrClient
		}
	};

	// FIXME not tested!
	private String interpret(byte[] archive) {
		// Decompress the bytes
		Inflater decompresser = new Inflater();
		String outputString = null;
		try {
			decompresser.setInput(archive, 0, archive.length);
			byte[] result = new byte[4000];
			int resultLength = decompresser.inflate(result);
			decompresser.end();

			// Decode the bytes into a String
			outputString = new String(result, 0, resultLength, "UTF-8");
		} catch (java.io.UnsupportedEncodingException ex) {
			Log.e(R.class.getName(), "UnsupportedEncodingException");
			ex.printStackTrace();
		} catch (java.util.zip.DataFormatException ex) {
			// This is not an archive actually, just plain text
			Log.e(R.class.getName(), "DataFormatException");
		}

		return outputString;
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, intent);
		if (result != null) {
			String decodedContent = interpret(result.getRawBytes());
			if (decodedContent == null)
				decodedContent = result.getContents();
			Intent captureMenuIntent = new Intent(this, CaptureMenuActivity.class);

			// XXX if the Oops is not in a zlib compressed format
			captureMenuIntent.putExtra(EXTRA_MESSAGE, decodedContent);
			startActivity(captureMenuIntent);		
		} else {
			Log.e("QrLog", "Scan result is NULL");
		}
	}

}
