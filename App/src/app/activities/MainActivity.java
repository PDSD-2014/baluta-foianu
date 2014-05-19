package app.activities;

import java.util.zip.Inflater;

import libs.IntentIntegrator;
import libs.IntentResult;
import services.QrClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import app.res.R;

public class MainActivity extends Activity {
	private QrClient rs;
	public final static String EXTRA_MESSAGE = "app.activities.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.scan).setOnClickListener(scanQRCode);
		findViewById(R.id.saved).setOnClickListener(viewSaved);
		rs = new QrClient("http://192.168.56.1:8080/");
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private final View.OnClickListener scanQRCode = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(
					MainActivity.this);
			// integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
			try {
				rs.postQr("uncod");
			} catch (Exception e) {
				e.printStackTrace();
			}
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

			Intent printActivity = new Intent(getApplicationContext(),
					PrintActivity.class);
			printActivity.putExtra("content", reply);
			startActivity(printActivity);
		}
	};

	// FIXME not tested!
	private String inflate(byte[] archive) {
	     // Decompress the bytes
	     Inflater decompresser = new Inflater();
	     String outputString = null;
	     try {
	     decompresser.setInput(archive, 0, archive.length);
	     byte[] result = new byte[100];
	     int resultLength = decompresser.inflate(result);
	     decompresser.end();

	     // Decode the bytes into a String
	     outputString = new String(result, 0, resultLength, "UTF-8");
	     } catch(java.io.UnsupportedEncodingException ex) {
	    	 Log.e(R.class.getName(), "UnsupportedEncodingException");
	    	 ex.printStackTrace();
	     } catch (java.util.zip.DataFormatException ex) {
	    	 Log.e(R.class.getName(), "DataFormatException");
	    	 ex.printStackTrace();
	     }
	     
			Intent printActivity = new Intent(getApplicationContext(),
					PrintActivity.class);
			printActivity.putExtra("content", outputString);
			startActivity(printActivity);
			
	     return outputString;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, intent);
		if (result != null) {
			String contents = result.getContents();
			Intent captureMenuIntent = new Intent(this, CaptureMenuActivity.class);

			// XXX if the Oops is not in a zlib compressed format
			captureMenuIntent.putExtra(EXTRA_MESSAGE, contents);
			startActivity(captureMenuIntent);		
		} else {
			Log.e("QrLog", "Scan result is NULL");
		}
	}

}
