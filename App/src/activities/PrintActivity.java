package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import app.res.R;

public class PrintActivity extends Activity {
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_print);
	      View v = findViewById(R.id.message);
	      TextView t = (TextView) v;
	      t.setText(getIntent().getStringExtra("content"));
	   }

}
