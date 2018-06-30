package nnk.com.asiancorpcare;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    public static final String[] titles = new String[] { "Red",
            "Blue", "Green", "Orange" ,"Black","Yellow","White","Pink" ,"Violet","Brown"};

    public static final Integer[] images = { R.drawable.flag_of_india,
            R.drawable.flag_of_indonesia, R.drawable.flag_of_iran,R.drawable.flag_of_iraq, R.drawable.flag_of_ireland,
            R.drawable.flag_of_india, R.drawable.flag_of_indonesia, R.drawable.flag_of_iran,R.drawable.flag_of_iraq,
            R.drawable.flag_of_ireland};

    ListView listView;
    List<RowItem> rowItems;
    Toolbar toolbar_dash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        toolbar_dash = (Toolbar) findViewById(R.id.toolbar_dash);
        if (toolbar_dash != null) {
            setSupportActionBar(toolbar_dash);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.listView);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_items, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Item " + (position + 1) + ": " + rowItems.get(position),
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
        Intent intent = new Intent(getApplicationContext(),TripExpenses.class);
        startActivity(intent);

    }
}



