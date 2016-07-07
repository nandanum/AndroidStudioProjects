package com.mnandanuri.mytodoapp;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mnandanuri.mytodoapp.model.ItemsList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import com.activeandroid.configur;

public class TodoActivity extends AppCompatActivity {
    ArrayList<ToDoDetails> items;
    //ArrayAdapter<ToDoAppAdater> itemsAdapter;

    ListView lvItems;
    private List<Button> buttons;
    private LayoutInflater mInflater;
    Set<String> hs = new HashSet<>();
    Button btnSubmit;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private final int REQUEST_CODE_ADD = 10;
    private final int REQUEST_CODE_UPDATE = 20;

    private int position;
    PopupMenu popup;
    SimpleDateFormat sdf;
    String currentDateandTime;
    ToDoAppAdater adapter;
    ToDoDetails tasks;
    com.activeandroid.Configuration dbConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo);


        lvItems = (ListView) findViewById(R.id.lvItems);

        items = new ArrayList<ToDoDetails>();

        String listString = items.toString();
        listString = listString.substring(1, listString.length() - 1);
        String newList[] = listString.split(",");
        Resources res = getResources();

        //itemsAdapter = new ToDoAppAdater(this, items);
        dbConfiguration = new Configuration.Builder(this).setDatabaseName("DBListItems.db").create();
        ActiveAndroid.initialize(dbConfiguration);
        adapter = new ToDoAppAdater(this, items);
        //       adapter.clear();

        // lvItems.setAdapter(itemsAdapter);
        List<ItemsList> dbListItems = getAll();
        for (ItemsList dbItem : dbListItems) {

            tasks = new ToDoDetails(dbItem.taskName);

            adapter.add(tasks);


        }

        lvItems.setAdapter(adapter);

        setUpListViewListener();

        setUpListViewClickListener();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private List<ItemsList> getAll() {
        ItemsList item = new ItemsList();

        return item.getAll();
    }

    private void setUpListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {

               /* List<ItemsList> deleteItem = ItemsList.removeItem(pos);
                for (ItemsList delItem : deleteItem) {
                    System.out.println(delItem.taskName
                    tasks=new ToDoDetails(delItem.taskName);

                    adapter.remove(tasks);
                }
                //---- writeItems();
                lvItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/

                position = lvItems.getPositionForView(view);
                view.setSelected(true);
                //dbConfiguration = new Configuration.Builder(this).setDatabaseName("DBListItems.db").create();
                //ActiveAndroid.initialize(dbConfiguration);

                //Delete Item based on the ID Selected

                List<ItemsList> dbId = ItemsList.getItemIdByTaskName(items.get(pos).toString());
                for (ItemsList db : dbId) {
                    ItemsList.removeItem(db.getId());
                    items.remove(position);
                    adapter.notifyDataSetChanged();

                }
/*                for (ItemsList dbItem : dbId) {
                  int dbitemId= dbItem.getId();
                }
*/
                //              List<ItemsList> delitem=ItemsList.removeItem(dbId.getId());



         /*       List<ItemsList> dbListItems = getAll();
                for (ItemsList dbItem : dbListItems) {
                    System.out.println(dbItem.taskName);
                    tasks=new ToDoDetails(dbItem.taskName);
                    adapter.add(tasks);
                }

                lvItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
                return true;
            }


        });
    }


    private void setUpListViewClickListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);

                String text = lvItems.getItemAtPosition(pos).toString();

                position = lvItems.getPositionForView(view);
                view.setSelected(true);
                List<ItemsList> dbId = ItemsList.getItemIdByTaskName(items.get(pos).toString());
                List<ItemsList> dbListItem = null;
                for (ItemsList db : dbId) {
                    dbListItem = ItemsList.getItemById(db.getId());
                }

                for (ItemsList dbItem : dbListItem) {

                    System.out.println(dbItem.taskName);

                    i.putExtra("TaskName", dbItem.taskName);
                    i.putExtra("TaskDate", dbItem.dateCreated);
                    i.putExtra("TaskNotes", dbItem.taskNotes);
                    i.putExtra("TaskStatus", dbItem.status);
                    i.putExtra("Id", dbItem.getId());
                }

                i.putExtra("CommandName", "Update");

                startActivityForResult(i, REQUEST_CODE_UPDATE);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();


        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Todo Page", // TODO: Define a title for the content shown.
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Intent addIntent = new Intent(TodoActivity.this, AddItemActivity.class);
                startActivityForResult(addIntent, REQUEST_CODE_ADD);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Todo Page", // TODO: Define a title for the content shown.
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

    @Override
    //Triggered when we receive data from the other application
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Boolean isPresent = false;
        String taskName = data.getExtras().getString("TaskName");
        String cmd = data.getExtras().getString("CommandName");
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK) {
            for (int i = 0; i < adapter.getCount(); i++) {

                if (taskName.equalsIgnoreCase(adapter.getItem(i).toString())) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {   //if not already exists
                tasks = new ToDoDetails(taskName);
                items.set(position, tasks);


                lvItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            adapter.clear();
            Boolean isItemPresent = false;
            List<ItemsList> dbListItems = getAll();
            for (ItemsList dbItem : dbListItems) {
                System.out.println(dbItem.taskName);
                tasks = new ToDoDetails(dbItem.taskName);
                adapter.add(tasks);
            }

            lvItems.setAdapter(adapter);


        }
    }
}




