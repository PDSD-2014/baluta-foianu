package app.activities;

import services.QrClient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import app.res.R;

public class CaptureMenuActivity extends Activity {
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_menu);

		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		findViewById(R.id.view).setOnClickListener(viewOops);
		findViewById(R.id.send).setOnClickListener(sendOops);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capture_menu, menu);
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
	
	public void sendMessage(View view) {
		Intent viewOopsIntent = new Intent(this, ViewOopsActivity.class);
		viewOopsIntent.putExtra(MainActivity.EXTRA_MESSAGE, message);
	    startActivity(viewOopsIntent);
	}
	
	private final View.OnClickListener viewOops = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			sendMessage(v);
		}
	};

	private final View.OnClickListener sendOops = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			sendMessage(v);
			MainActivity.rs.postQr(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
		}
	};
}
