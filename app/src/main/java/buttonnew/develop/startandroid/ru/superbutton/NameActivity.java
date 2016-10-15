package buttonnew.develop.startandroid.ru.superbutton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends Activity implements OnClickListener {

    EditText etName;
    Button btnOK;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        setTitle("Новое слово");

        etName = (EditText) findViewById(R.id.etName);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intres = new Intent();
        intres.putExtra("name", etName.getText().toString());
        setResult(RESULT_OK, intres);

        Intent intent = new Intent(this, ExampleActivity.class);
        startActivity(intent);

        //startActivityForResult(intent, 1);
       // Log.d(LOG_TAG, etName.getText().toString());
        finish();
    }

 //   @Override
 //   public void onClick(View v) {
 //       Intent intent = new Intent();
 //       intent.putExtra("name", etName.getText().toString());
 //       setResult(RESULT_OK, intent);
 //       finish();
 //   }
}