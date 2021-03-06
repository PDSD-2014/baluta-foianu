package ro.pub.cs.toops.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import app.res.R;

public class ConfigActivity extends Activity {
	SharedPreferences app_preferences;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);

		app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		findViewById(R.id.savecfg).setOnClickListener(save);

		String server = app_preferences.getString("server",
				"http://localhost:8080/");

		String my_email = app_preferences.getString("email", "");

		EditText serverText = (EditText) findViewById(R.id.text);
		serverText.setText(server);

		EditText emailText = (EditText) findViewById(R.id.email);
		emailText.setText(my_email);

	}

	private final View.OnClickListener save = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SharedPreferences.Editor editor = app_preferences.edit();
			String server = ((EditText) findViewById(R.id.text)).getText()
					.toString();
			String email = ((EditText) findViewById(R.id.email)).getText()
					.toString();

			/* Check server/IP validity */
			String validServer = "^http://[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,}):[1-9]([0-9])*";
			if (!server.matches(validServer + "/")) {
				if (server.matches(validServer)) {
					server += "/";
				} else {
					String validIP =
							"^http://([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
							"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
							"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
							"([01]?\\d\\d?|2[0-4]\\d|25[0-5]):[1-9]([0-9])*";
					if (!server.matches(validIP + "/")) {
						if (server.matches(validIP)) {
							server += "/";
						} else {
							Toast.makeText(getApplicationContext(),
									"Server configuration is invalid",
									Toast.LENGTH_SHORT).show();
							return;
						}
					}

					if (!email.matches(".+@.+[.].+")) {
						Toast.makeText(getApplicationContext(),
								"Email configuration is invalid", Toast.LENGTH_SHORT)
								.show();
						return;
					}
				}
			}

			editor.putString("server", server);
			editor.putString("email", email);
			editor.commit();
			Toast.makeText(getApplicationContext(), "Settings saved",
					Toast.LENGTH_SHORT).show();
		}
	};
}