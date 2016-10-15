package buttonnew.develop.startandroid.ru.superbutton;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity  {
    //implements CompoundButton.OnCheckedChangeListener
    private static final int CM_DELETE_ID = 1;

    Switch switchAB;

    EditText etText;

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";


    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_IMAGE = "image";
    final String LOG_TAG = "myLogs";

    ArrayList<Map<String, Object>> catnames = new ArrayList<>();

    ListView lvSimple;
    SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> data;
    Map<String, Object> m;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // упаковываем данные в понятную для адаптера структуру
        data = new ArrayList<Map<String, Object>>();
        for (int i = 1; i < 5; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, "sometext " + i);
            m.put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher);
            data.add(m);
        }
        catnames = data;

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_IMAGE };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvText, R.id.ivImg };

        // создаем адаптер
        sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);

        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
        registerForContextMenu(lvSimple);

        //  Switch zSwitch = (Switch) findViewById(R.id.switchAB);
        //zSwitch.setChecked(true);
        // if (zSwitch != null) {
        //     Log.d(LOG_TAG, "IN");
        //     zSwitch.setOnCheckedChangeListener(this);
        // }

        //Log.d(LOG_TAG, "Point 1");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.d(LOG_TAG, "Point 2");

        getMenuInflater().inflate(R.menu.main, menu);
        switchAB = (Switch)menu.findItem(R.id.myswitch).getActionView().findViewById(R.id.switchAB);
        etText = (EditText)menu.findItem(R.id.statusTextview).getActionView().findViewById(R.id.balance_text);
        loadText();

        switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplication(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor = getSharedPreferences("button.develop.startandroid.ru.plusbutton", MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", true);
                    editor.commit();
                } else {
                    Toast.makeText(getApplication(), "OFF", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor = getSharedPreferences("button.develop.startandroid.ru.plusbutton", MODE_PRIVATE).edit();
                    editor.putBoolean("NameOfThingToSave", false);
                    editor.commit();
                }
            }
        });

        SharedPreferences sharedPrefs = getSharedPreferences("button.develop.startandroid.ru.plusbutton", MODE_PRIVATE);
        switchAB.setChecked(sharedPrefs.getBoolean("NameOfThingToSave", true));


        return true;
    }

    public void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        if (savedText == "") {
            savedText = "60";
        }
        etText.setText(savedText);
        Toast.makeText(this, "Text loaded" + savedText, Toast.LENGTH_SHORT).show();
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, etText.getText().toString());
        ed.commit();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }

    public void onButtonClick(View v) {
        Intent intent = new Intent(this, NameActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "wearetesting");
        if (data == null) {return;}
        String name = data.getStringExtra("name");
        m = new HashMap<String, Object>();
        m.put(ATTRIBUTE_NAME_TEXT, name);
        m.put(ATTRIBUTE_NAME_IMAGE, R.drawable.ic_launcher);
        catnames.add(m);
        sAdapter.notifyDataSetChanged();
       Log.d(LOG_TAG, name);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем инфу о пункте списка
            AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
            // удаляем Map из коллекции, используя позицию пункта в списке
            data.remove(acmi.position);
            // уведомляем, что данные изменились
            sAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }



}