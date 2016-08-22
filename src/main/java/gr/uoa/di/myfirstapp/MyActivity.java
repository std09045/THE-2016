package gr.uoa.di.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "gr.uoa.di.MESSAGE";
    public final static String RESULT_MESSAGE = "gr.uoa.di.RESULTMESSAGE";
    
    //For displaying what user just wrote (as in tutorials)
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //For calling Restful service and displaying result with the next activity
    public void searchMessage(View view){
        new RestThread(this).execute("http://192.168.1.2:8080/CustomerDB/webresources/entities.customer/bubbles");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }
    
    //Menu with 3 buttons (see main/res/menu/mainmenu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    //Just for testing the menu. Does nothing for now
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
        case R.id.mylist:
            //newGame();
            return true;
        case R.id.allprod:
            new RestThread(this).execute("http://192.168.1.2:8080/CustomerDB/webresources/entities.customer/bubbles");
            return true;
        case R.id.settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}