package activities;

import libs.IntentIntegrator;
import libs.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import app.res.R;

public class MainActivity extends Activity {

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
			TextView label = new TextView(this);
			label.setText(new String(result.getRawBytes()));		
		} else {
			Log.e("Res:", "NULL");
		}
	}

}
