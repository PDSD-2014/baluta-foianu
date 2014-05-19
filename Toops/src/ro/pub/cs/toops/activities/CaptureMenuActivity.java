package ro.pub.cs.toops.activities;

import ro.pub.cs.toops.utilities.FileHandler;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
		findViewById(R.id.send).setOnClickListener(sendOops);
		findViewById(R.id.save).setOnClickListener(saveOops);

		/* Set display for Oops */
		TextView oopsView = (TextView) findViewById(R.id.displayoops);

		Intent intent = getIntent();
		message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		oopsView.setMovementMethod(new ScrollingMovementMethod());
		oopsView.setText(message
				+ "\n\niushfouabevrnlvnbsoiuqbwadcskjfhb12yu4yt2387ry5q9u"
				+ "2wnkdjssnvskahjbdfioauskdbvfan" + "\n\n\nwkjrbquyrvibwsfkd"
				+ "enfliaejrbfjkbfluiaq2rfwesd\n\n\n");
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

	private final View.OnClickListener saveOops = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			FileHandler.saveOopsToFile(message);
		}
	};

	private final View.OnClickListener sendOops = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e("mesaje", "Trying to post: " + message);
			MainActivity.rs.postQr(message);
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
