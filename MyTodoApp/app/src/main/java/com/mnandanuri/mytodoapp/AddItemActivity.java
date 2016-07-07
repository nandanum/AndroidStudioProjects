package com.mnandanuri.mytodoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;

import com.mnandanuri.mytodoapp.model.ItemsList;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by mnandanuri on 6/28/2016.
 */
public class AddItemActivity extends AppCompatActivity {

    private GoogleApiClient client;
    EditText etTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);
        etTaskName = (EditText) findViewById(R.id.etTaskName);
        etTaskName.requestFocus();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CalendarView etTaskDate;
        int id = item.getItemId();
        switch (id) {
            case R.id.saveItem:
                EditText etTaskName = (EditText) findViewById(R.id.etTaskName);
                etTaskDate = (CalendarView) findViewById(R.id.etTaskDate);
                EditText etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
                EditText etTaskStatus = (EditText) findViewById(R.id.etTaskStatus);
                java.text.DateFormat dateFormat =
                        android.text.format.DateFormat.getDateFormat(getApplicationContext());

                // Prepare data intent
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("TaskName", etTaskName.getText().toString());
                String date = dateFormat.format(etTaskDate.getDate());
                data.putExtra("DueDate", date);
                data.putExtra("TaskNotes", etTaskNotes.getText().toString());
                data.putExtra("Status", etTaskStatus.getText().toString());

                data.putExtra("REQUEST_CODE", 10);
                data.putExtra("CommandName", "Add");
                ItemsList db = new ItemsList();
                db.taskName = etTaskName.getText().toString();
                db.dateCreated = date;
                db.taskNotes = etTaskNotes.getText().toString();
                db.status = etTaskStatus.getText().toString();
                db.save();

                //  data.putExtra("Exit","false");
                setResult(RESULT_OK, data);// set result code and bundle data for response
                finish(); // closes the activity, pass data to parent

                return true;

            case R.id.cancelItem:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Cancel Item");
                builder.setMessage("Item will not be updated in the list");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent intent = new Intent(AddItemActivity.this, TodoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();


                return true;
            // System.exit(0);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saveitems, menu);
        return true;
    }

}