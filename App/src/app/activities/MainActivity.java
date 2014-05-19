package app.activities;

import java.util.zip.Inflater;

import libs.IntentIntegrator;
import libs.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import app.res.R;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "app.activities.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.scan).setOnClickListener(scanQRCode);
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
			System.out.println("Integrator");
			IntentIntegrator integrator = new IntentIntegrator(
					MainActivity.this);
			integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
		}
	};

	// FIXME not tested!
	private static String inflate(byte[] archive) {
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
	     return outputString;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
				resultCode, intent);
		Log.e("ceva", "Got result");
		if (result != null) {
			String contents = result.getContents();
			System.out.println(contents);
			Log.e("Content: ", contents);
			
			Log.e("Bytes: ", new String(result.getRawBytes()));
			Intent captureMenuIntent = new Intent(this, CaptureMenuActivity.class);
			// XXX if the Oops is not in a zlib compressed format
			captureMenuIntent.putExtra(EXTRA_MESSAGE, contents);
		    startActivity(captureMenuIntent);		
		} else {
			Log.e("Res:", "NULL");
		}
	}

}
