/*
NARPassword for Android - Application to generate a non-random password
Copyright (C) 2013-2019 Hugues Johnson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.huguesjohnson.narpas;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCopy=(Button)findViewById(R.id.buttonCopy);
        buttonCopy.setOnClickListener(onCopyClicked);
        SeekBar seekbarPasswordLength=(SeekBar)findViewById(R.id.seekBarPasswordlength);
        final TextView textViewPasswordLengthValue=(TextView)findViewById(R.id.textViewPasswordlengthValue);
        textViewPasswordLengthValue.setText(" ["+((seekbarPasswordLength.getProgress()+1)*8)+"]");
        seekbarPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){ /* not implemented */ }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){ /* not implemented */ }
            @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
                textViewPasswordLengthValue.setText(" ["+((progress+1)*8)+"]");
            }
        });
    }

    public View.OnClickListener onCopyClicked =new View.OnClickListener(){
        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        public void onClick(View v){
            EditText editTextPassphrase=(EditText)findViewById(R.id.editTextPassphrase);
            EditText editTextPasswordname=(EditText)findViewById(R.id.editTextPasswordname);
            CheckBox checkBoxUselcase=(CheckBox)findViewById(R.id.checkBoxUselcase);
            CheckBox checkBoxUseucase=(CheckBox)findViewById(R.id.checkBoxUseucase);
            CheckBox checkBoxUsenumbers=(CheckBox)findViewById(R.id.checkBoxUsenumbers);
            CheckBox checkBoxUsespecialcharacters=(CheckBox)findViewById(R.id.checkBoxUsespecialcharacters);
            SeekBar seekbarPasswordLength=(SeekBar)findViewById(R.id.seekBarPasswordlength);
            //generate password
            String passPhrase=editTextPassphrase.getText().toString();
            String passwordName=editTextPasswordname.getText().toString();
            boolean useLCase=checkBoxUselcase.isChecked();
            boolean useUCase=checkBoxUseucase.isChecked();
            boolean useNumbers=checkBoxUsenumbers.isChecked();
            boolean useSpecialCharacters=checkBoxUsespecialcharacters.isChecked();
            int passwordLength=seekbarPasswordLength.getProgress()+1;
            String password=Narpas.generatePassword(passPhrase,passwordName,useLCase,useUCase,useNumbers,useSpecialCharacters,passwordLength);
            //store password on the clipboard
            int sdk=android.os.Build.VERSION.SDK_INT;
            if(sdk< Build.VERSION_CODES.HONEYCOMB){
                android.text.ClipboardManager clipboard=(android.text.ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(password);
            }else{
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = ClipData.newPlainText("simple text", password);
                clipboard.setPrimaryClip(clip);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
