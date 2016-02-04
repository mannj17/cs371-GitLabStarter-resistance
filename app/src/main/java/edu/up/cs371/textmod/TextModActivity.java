package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.TextView;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class TextModActivity extends ActionBarActivity implements View.OnClickListener{

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    int currentPostion;
    private ImageView imageView; // the view that shows the image
    private Button copyName;

    private Button reverse;
    private TextView editText;
    protected Button upperCase = null;
    protected EditText sentence = null;
    protected Button lowerCase = null;
    String[] spinnerNames;
    protected Button clear = null;
    protected EditText editTV= null;
    protected Button randChar = null;
    protected Random character;
    String[] randoms;

    protected Button punctuation = null;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        clear = (Button)findViewById(R.id.clearButton); //sets the clear button and listener
        clear.setOnClickListener(this);

        editTV = (EditText)findViewById(R.id.editText);

        punctuation = (Button)findViewById(R.id.buttonNoPunctuation);
        punctuation.setOnClickListener(this);


        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (TextView)findViewById(R.id.editText);
        editText = (TextView)findViewById(R.id.editText);
        copyName = (Button)findViewById(R.id.copyName);
        copyName.setOnClickListener(this);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);

            reverse = (Button)findViewById(R.id.buttonReverse);
            reverse.setOnClickListener(this);

        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

        upperCase = (Button)findViewById(R.id.upper);
        upperCase.setOnClickListener(this);

        lowerCase = (Button)findViewById(R.id.lower);
        lowerCase.setOnClickListener(this);

        randChar = (Button)findViewById(R.id.randomCharacter);
        randChar.setOnClickListener(this);

        sentence = (EditText)findViewById(R.id.editText);

        randoms = getResources().getStringArray(R.array.random_chars);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
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
    }

    @Override
    public void onClick(View v) {
        Random rand = new Random();
        if (v.getId() == R.id.upper) {
            CharSequence temp = sentence.getText();
            String temp2 = temp.toString();
            temp2 = temp2.toUpperCase();
            CharSequence temp3 = (CharSequence) temp2;
            sentence.setText(temp3);
        }
        if (v.getId() == R.id.lower) {
            CharSequence temp = sentence.getText();
            String temp2 = temp.toString();
            temp2 = temp2.toLowerCase();
            CharSequence temp3 = (CharSequence) temp2;
            sentence.setText(temp3);
        }
        if ((v.getId() == R.id.copyName)) {
            String val = (String) editText.getText().toString();
            editText.setText(val + spinnerNames[currentPostion]);
        }
        if (v.getId() == R.id.clearButton) {
            editTV.setText("");

        }
        if (v.getId() == R.id.buttonReverse) {
            String input = this.editText.getText().toString();
            StringBuffer buffer = new StringBuffer(input);
            buffer.reverse();
            this.editText.setText(buffer);
        }
        if (v.getId() == R.id.randomCharacter) {
            CharSequence temp = sentence.getText();
            int tempChar = rand.nextInt(randoms.length);
            int tempSpot = rand.nextInt(temp.length());
            CharSequence temp2 = temp.subSequence(0, tempSpot);
            String temp3 = temp2.toString();
            CharSequence temp4 = temp.subSequence(tempSpot + 1, temp.length());
            String temp5 = temp4.toString();
            String finalTemp = temp3 + randoms[tempChar] + temp5;
            CharSequence finalChar = (CharSequence) finalTemp;
            sentence.setText(finalTemp);
        }
        if (v.getId() == R.id.buttonNoPunctuation) {
            String test = this.editText.getText().toString();
            test = test.replaceAll("[\\!\\.\\,\\?]", "");
            this.editText.setText(test);
        }
    }
    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
            currentPostion = position;
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }
}
