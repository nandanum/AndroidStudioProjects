package com.mnandanuri.mytodoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TodoActivity extends AppCompatActivity {
   ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private List<Button> buttons;
    private LayoutInflater mInflater;
    Set<String> hs = new HashSet<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private final int REQUEST_CODE_UPDATE = 20;

    private int position;
/*
    private static final int[] BUTTON_IDS = {
            R.id.btnDeleteOne,
            R.id.btnDeleteTwo,
            R.id.btnDeleteThree,
            R.id.btnDeleteFour,

    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_todo);
        readItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        items.add("First Item");
        items.add("Second Item");
        String listString =items.toString();
        listString=listString.substring(1,listString.length()-1);
        String newList[]=listString.split(",");
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

        lvItems.setAdapter(itemsAdapter);

        setUpListViewListener();

        setUpListViewClickListener();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    private void setUpListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }


        });
    }





    private void setUpListViewClickListener() {
        // ListView lv =getListView();
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);

                String text = lvItems.getItemAtPosition(pos).toString();
                position = lvItems.getPositionForView(view);
                view.setSelected(true);
                //Object o = parent.getItemAtPosition(pos);
                i.putExtra("Text", text);
                i.putExtra("ID",pos);
                //i.putExtra()

                i.setType("text/plain");

                //  i.putExtra("Pos",pos);// pass arbitrary data to launched activity
                startActivityForResult(i, 20);
             //   startActivityForResult(i,REQUEST_CODE_UPDATE);

            }
        });
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));


        } catch (IOException ex) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");

        try {
            FileUtils.writeLines(fileDir, items);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


 /*   public View getView(int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            LinearLayout view = (LinearLayout) LinearLayout.inflate(this,
                    R.layout.message, null);
            Log.d("SeenDroid", String.format("Get view %d", position));
            TextView title = new TextView(view.getContext());
            title.setText(this.items.get(position).getTitle());
            view.addView(title);
            return view;
        } else {
            LinearLayout view = (LinearLayout) convertView;
            TextView title = (TextView) view.getChildAt(0);
            title.setText(this.items.get(position).getTitle());
            return convertView;
        }
    }
*/

   public void onSubmit(View view) {
       /*EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
       itemsAdapter.add(itemText);
        etNewItem.setText("");
       items.add(itemText);
       lvItems.setAdapter(itemsAdapter);
       itemsAdapter.notifyDataSetChanged();*/

       //items.clear();
       //items.addAll(hs);
     //  items.clear();
       EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
       Boolean isItemPresent=false;
       String itemText = etNewItem.getText().toString();
     //  Adapter.getCount()
      for (int i = 0; i < itemsAdapter.getCount(); i++) {

          if (itemText .equalsIgnoreCase(itemsAdapter.getItem(i))) {
              isItemPresent = true;
              break;
          }
      }
            if(!isItemPresent){   //already exists
           //   items.add(itemText);
                itemsAdapter.add(itemText);
               etNewItem.setText("");

                lvItems.setAdapter(itemsAdapter);
               //itemsAdapter.notifyDataSetChanged();
             //  writeItems();

           }
       else {
                etNewItem.setText("");
            }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        //Update Item
        Boolean isPresent = false;
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK) {
            String text = data.getExtras().getString("Text");
            for (int i = 0; i < itemsAdapter.getCount(); i++) {

                if (text.equalsIgnoreCase(itemsAdapter.getItem(i))) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {   //already exists
                //   items.add(itemText);
                items.set(position, text);
                //items.add(text);
                lvItems.setAdapter(itemsAdapter);
                itemsAdapter.notifyDataSetChanged();
            }
        /*
    if (requestCode == REQUEST_CODE_UPDATE  && resultCode==RESULT_OK) {
            // Extract name value from result extras
            String text = data.getExtras().getString("Text");
        // items.set(position, text);
        items.set(position, text);
        //items.add(text);
            lvItems.setAdapter(itemsAdapter);


            itemsAdapter.notifyDataSetChanged();

    }
*/

        }
    }


    }
