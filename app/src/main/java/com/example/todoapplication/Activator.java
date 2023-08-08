package com.example.todoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todoapplication.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Represents the Activator of the Application
 * </p>
 * @author Roshan
 * @version 1.0
 */
public class Activator extends AppCompatActivity {

    private List<Project> list;
    private DrawerLayout drawerLayout;
    private ArrayAdapter<Project> arrayAdapter;
    private static Long projectId;

    /**
     * <p>
     *     Represents the
     * </p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView backButton = findViewById(R.id.backButton);
        Button addButton = findViewById(R.id.createList);
        drawerLayout = findViewById(R.id.Layout);
        ImageButton menuButton = findViewById(R.id.menuButton);
        ListView listView = findViewById(R.id.nameListView);
        list = new ArrayList<>();
        projectId = 1L;
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                obtainName();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final Project projectName = list.get(i);
                final Intent intent = new Intent(Activator.this, ProjectActivity.class);
                intent.putExtra("name", projectName.getLabel());
                startActivity(intent);
                }
        });
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void obtainName() {
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this).setTitle("Add Name").setView(text).setPositiveButton("Ok", (dialog, which) -> {
            final String name = text.getText().toString().trim();

            if (!name.isEmpty()) {
                final Project project = new Project(String.valueOf(projectId++), name);
                list.add(project);
                arrayAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Enter project name", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", null).create().show();
    }
}