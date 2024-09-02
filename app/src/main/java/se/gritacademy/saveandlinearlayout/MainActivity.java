package se.gritacademy.saveandlinearlayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    EditText et;
    Button btn;

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tv.setText(savedInstanceState.getString("Key2","[Text not found]"));
        Log.d("alrik", "onRestoreInstanceState: RESTORE");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Key2","test2");
        Log.d("alrik", "onSaveInstanceState: SAVE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preferance), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d("alrik", sharedPref.getString("Key", "default text if null"));


        tv = findViewById(R.id.text);
        et = findViewById(R.id.editTextText);
        btn = findViewById(R.id.button);
        //när man startar appen så sätter textVeiwn texten från sharedpref
        //tv.setText(sharedPref.getString("Key", "[no text found]"));

        btn.setOnClickListener((e) -> {
            String inputtedText = et.getText().toString();

            if (inputtedText == "R") {
                editor.remove("Key");
                editor.apply();
            } else {
                editor.putString("Key", inputtedText).apply();
                tv.setText(inputtedText);
                Toast.makeText(MainActivity.this, "saved:" + inputtedText, Toast.LENGTH_SHORT).show();
            }

            });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}