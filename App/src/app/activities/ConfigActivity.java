package app.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
		;

		String server = app_preferences.getString("server",
				"http://localhost:8080/");

		EditText text = (EditText) findViewById(R.id.text);
		text.setText(server);

	}

	private final View.OnClickListener save = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SharedPreferences.Editor editor = app_preferences.edit();
			String server = ((EditText) findViewById(R.id.text)).getText()
					.toString();
			if (!server.matches("http://.*:[0-9]*/")) {
				Log.e("mesaje", "Server config is invalid");
				return;
			}
				
			editor.putString("server", server);
			editor.commit();
		}
	};
}