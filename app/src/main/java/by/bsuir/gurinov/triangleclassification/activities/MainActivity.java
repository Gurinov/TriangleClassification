package by.bsuir.gurinov.triangleclassification.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.bsuir.gurinov.triangleclassification.R;
import by.bsuir.gurinov.triangleclassification.fragments.DrawFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.actionMenu)
    FloatingActionMenu floatingActionMenu;

    @BindView(R.id.info)
    FloatingActionButton infoButton;

    @BindView(R.id.save_to_file)
    FloatingActionButton saveButton;

    @BindView(R.id.load_from_file)
    FloatingActionButton loadButton;

    @BindView(R.id.classify)
    Button classifyButton;

    @BindView(R.id.side1)
    EditText side1;

    @BindView(R.id.side2)
    EditText side2;

    @BindView(R.id.side3)
    EditText side3;

    private static final String FILE_NAME = "data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((side1.getText().toString().equals("")) || (side2.getText().toString().equals("")) || (side3.getText().toString().equals("")))
                {
                    Toast.makeText(v.getContext(), "Заполните поля", Toast.LENGTH_LONG).show();
                } else {
                    DrawFragment fragment = new DrawFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("side1", Integer.parseInt(side1.getText().toString()));
                    bundle.putInt("side2", Integer.parseInt(side2.getText().toString()));
                    bundle.putInt("side3", Integer.parseInt(side3.getText().toString()));
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DrawFragment()).commit();
    }


    public void save() {
        if ((side1.getText().toString().equals("") || side2.getText().toString().equals("") || (side3.getText().toString().equals("")))) {
            Toast.makeText(MainActivity.this, "Заполните поля", Toast.LENGTH_LONG).show();
        } else {
            Gson gson = new Gson();
            ArrayList<String> sides = new ArrayList<>();
            sides.add(side1.getText().toString());
            sides.add(side2.getText().toString());
            sides.add(side3.getText().toString());

            String jsonString = gson.toJson(sides);

            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = MainActivity.this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fileOutputStream.write(jsonString.getBytes());
                Toast.makeText(MainActivity.this, "Файл успешно сохранен", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void load() {
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = MainActivity.this.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            ArrayList<String> sides = gson.fromJson(streamReader, ArrayList.class);
            side1.setText(sides.get(0));
            side2.setText(sides.get(1));
            side3.setText(sides.get(2));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
