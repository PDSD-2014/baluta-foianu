package ro.pub.cs.toops.activities;

import ro.pub.cs.toops.utilities.FileHandler;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.res.R;

public class CaptureMenuActivity extends Activity {
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_menu);
		findViewById(R.id.sendS).setOnClickListener(sendToWS);
		findViewById(R.id.sendE).setOnClickListener(sendToEmail);
		findViewById(R.id.save).setOnClickListener(saveOops);

		/* Set display for Oops */
		TextView oopsView = (TextView) findViewById(R.id.displayoops);

		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		oopsView.setMovementMethod(new ScrollingMovementMethod());
		oopsView.setText(message);
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
			Toast.makeText(getApplicationContext(), "Please launch settings from home menu.", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private final View.OnClickListener saveOops = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			FileHandler.saveOopsToFile(message);
			Toast.makeText(getApplicationContext(), "Oops saved", Toast.LENGTH_SHORT).show();
		}
	};

	private final View.OnClickListener sendToWS = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			MainActivity.rs.postQr(message);
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
		}
	};

	private final View.OnClickListener sendToEmail = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			sendEmail(message);
		}
	};

	private final void sendEmail(String message) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
		intent.putExtra(Intent.EXTRA_TEXT, message);
		intent.setData(Uri.parse("mailto:dragos.foianu@gmail.com"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	    startActivity(Intent.createChooser(intent, "Send email using..."));
	}
}
