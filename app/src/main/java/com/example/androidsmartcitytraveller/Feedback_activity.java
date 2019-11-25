package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Feedback_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activity);
    }

    public void send_click(View v)
    {
        EditText name=(EditText)findViewById(R.id.editText);
        EditText email=(EditText)findViewById(R.id.editText2);
        EditText feedback=(EditText)findViewById(R.id.feed_edittext);


        if(name.getText().toString().equals(""))
            name.setError("Mandatory Field");
        else if(email.getText().toString().equals(""))
            email.setError("Mandatory Field");
        else if(feedback.getText().toString().equals(""))
            feedback.setError("Mandatory Field");

        else
        {
            Intent i=new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL,new String[]{"joshirricha@gmail.com"});
            i.putExtra(Intent.EXTRA_TEXT,"Dear Developer, \n" +feedback.getText().toString()
                    +"\n \n \nregards "+email.getText().toString());

            try{
                startActivity(Intent.createChooser(i,"send mail"));
            }catch(android.content.ActivityNotFoundException ex)
            {
                Toast.makeText(this,"no mail app found", Toast.LENGTH_SHORT).show();
            }catch(Exception ex)
            {
                Toast.makeText(this,"unexpected error"+ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

