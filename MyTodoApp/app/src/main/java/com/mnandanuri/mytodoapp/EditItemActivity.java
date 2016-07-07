package com.mnandanuri.mytodoapp;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mnandanuri.mytodoapp.model.ItemsList;

public class EditItemActivity extends AppCompatActivity {
    //TextView txt;
    EditText etName;
    ArrayAdapter<String> itemsAdapter;
    String itemData;
    private final int REQUEST_CODE = 20;

    private final int REQUEST_CODE_UPDATE = 20;
    private GoogleApiClient client;
    EditText etTaskName;
    CalendarView etTaskDate;
    EditText etTaskNotes;
    EditText etTaskStatus;
    private int yr, mon, dy;
    long pos;
    private Calendar selectedDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private  Button datePickerButton;
    @TargetApi(Build.VERSION_CODES.N)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etTaskName = (EditText) findViewById(R.id.etTaskName);
        etTaskName.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etTaskName, InputMethodManager.SHOW_IMPLICIT);
        etTaskDate = (CalendarView) findViewById(R.id.etTaskDate);
        etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        etTaskStatus = (EditText) findViewById(R.id.etTaskStatus);
        String itemData;

        Intent i = getIntent();
        String receivedAction = i.getAction();
        // fetch value from key-value pair and make it visible on TextView.

        etTaskName.setText(i.getStringExtra("TaskName"));
        // etTaskDate.setText(i.getStringExtra("TaskDate"));


        etTaskNotes.setText(i.getStringExtra("TaskNotes"));
        etTaskStatus.setText(i.getStringExtra("TaskStatus"));
        pos=i.getLongExtra("Id",1);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edititems, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.saveItem:
                // Prepare data intent
                final Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("TaskName", etTaskName.getText().toString());

                data.putExtra("TaskNotes", etTaskNotes.getText().toString());
                data.putExtra("TaskStatus", etTaskStatus.getText().toString());
                data.putExtra("REQUEST_CODE", REQUEST_CODE_UPDATE);
                data.putExtra("CommandName", "Update");

                final ItemsList db = new ItemsList();

                db.setTaskName(etTaskName.getText().toString());
                java.text.DateFormat dateFormat =
                        android.text.format.DateFormat.getDateFormat(getApplicationContext());
                String dateParam=dateFormat.format(etTaskDate.getDate());
                db.setTaskDate(dateParam);
               // db.setTaskDate(formattedDate);
                db.setNotes(etTaskNotes.getText().toString());
                db.setStatus(etTaskStatus.getText().toString());


                db.updateItem(db, pos);
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
                        Intent intent = new Intent(EditItemActivity.this, TodoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //If item to be cancelled ,Notify user ,pass intent data with cmd set to Cancel to identify the call on the receiving end.
    public void onCancel(View view) {
        //  EditText etName = (EditText) findViewById(R.id.etMultiLineText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Cancel Item");
        builder.setMessage("Item will not be updated in the list");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
                /*Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("Text", itemData);

                data.putExtra("REQUEST_CODE", 50);
                data.putExtra("CommandName", "Cancel");
                setResult(RESULT_OK, data);// set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
*/
                Intent intent = new Intent(EditItemActivity.this, TodoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        AlertDialog alert = builder.create();
        alert.show();


        // Prepare data intent
/*        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("Text", item);

        data.putExtra("REQUEST_CODE", 50);
        setResult(RESULT_OK, data);// set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
*/
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EditItem Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.mnandanuri.mytodoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EditItem Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.mnandanuri.mytodoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
