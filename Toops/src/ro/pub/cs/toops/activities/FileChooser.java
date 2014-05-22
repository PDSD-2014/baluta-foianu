package ro.pub.cs.toops.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.pub.cs.toops.utilities.FileHandler;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends ListActivity {
	private File file;
	private List<String> myList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myList = new ArrayList<String>();

		file = FileHandler.getDocumentsStorageDir();
		File list[] = file.listFiles();

		for (int i = 0; i < list.length; i++) {
			myList.add(list[i].getName());
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, myList));

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		File temp_file = new File(file, myList.get(position));

		if (!temp_file.isFile()) {
			file = new File(file, myList.get(position));
			File list[] = file.listFiles();

			myList.clear();

			for (int i = 0; i < list.length; i++) {
				myList.add(list[i].getName());
			}
			Toast.makeText(getApplicationContext(), file.toString(),
					Toast.LENGTH_LONG).show();
			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, myList));

		} else {
			// XXX send the file to display
			Intent viewOopsIntent = new Intent(this, CaptureMenuActivity.class);
			startActivity(viewOopsIntent);
		}

	}

	@Override
	public void onBackPressed() {
		String parent = file.getParent().toString();
		file = new File(parent);
		File list[] = file.listFiles();

		myList.clear();

		for (int i = 0; i < list.length; i++) {
			myList.add(list[i].getName());
		}
		Toast.makeText(getApplicationContext(), parent, Toast.LENGTH_LONG)
				.show();
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, myList));

	}
}