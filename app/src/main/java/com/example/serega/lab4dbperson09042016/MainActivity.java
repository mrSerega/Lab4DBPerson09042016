package com.example.serega.lab4dbperson09042016;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView _view;
    public static DB db;
    private Context mContext;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    myListAdapter myAdapter;

    private int ADD_ACTIVITY = 0;
    private int UPDATE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        db = new DB(this);
        _view = (ListView) findViewById(R.id.lv_view);
        myAdapter = new myListAdapter(mContext, db.selectAll());
        _view.setAdapter(myAdapter);
        registerForContextMenu(_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(mContext, AddActivity.class);
                startActivityForResult(i, ADD_ACTIVITY);
                //updateList();
                return true;
            case R.id.drop:
                db.deleteAll();
                //updateList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.edit:
                Intent i = new Intent(mContext, AddActivity.class);
                Person md = db.selectFirst(info.id);
                //i.putExtra("Person",md);
                startActivityForResult(i,UPDATE_ACTIVITY);
                //updateList();
                return true;
            case R.id.delete:
                db.delete(info.id);
                //updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateList(){
        myAdapter.setArrayMyData(db.selectAll());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == Activity.RESULT_OK){
            Person md = (Person) data.getExtras().getSerializable("Person");
            if(requestCode == UPDATE_ACTIVITY)
                db.update(md);
            else
                db.insert(md.get_name(), md.get_mark());
            updateList();
        }

    }

    class myListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private ArrayList<Person> arrayMyPerson;

        public myListAdapter (Context ctx, ArrayList<Person> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        public ArrayList<Person> getArrayMyData(){
            return arrayMyPerson;
        }

        public void setArrayMyData(ArrayList<Person> arrayMyData){
            this.arrayMyPerson=arrayMyData;
        }

        public int getCount(){
            return arrayMyPerson.size();
        }

        public Object getItem (int position){
            return position;
        }

        public long getItemId (int position){
            Person md = arrayMyPerson.get(position);
            if(md != null){
                return md.get_id();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent){

            if(convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);

            TextView vName = (TextView)convertView.findViewById(R.id.ed_name);
            TextView vMark = (TextView)convertView.findViewById(R.id.ed_mark);

            Person md = arrayMyPerson.get(position);
            vName.setText(md.get_name());
            vMark.setText(md.get_mark());

            return convertView;

        }

    }

}
