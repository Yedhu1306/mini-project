package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class questionnaire1 extends AppCompatActivity {

    public Button but1;
    public RadioGroup radioGroup;
    String response;
    public void init()
    {
        but1 = (Button) findViewById(R.id.subQuestion1);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup1);
      //  yes=(RadioButton)findViewById(R.id.yes);
        //no=(RadioButton)findViewById(R.id.no);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                /*if(radioGroup.getCheckedRadioButtonId()==-1){//Grp is your radio group object
                    Toast.makeText(questionnaire1.this, "Select Option", Toast.LENGTH_SHORT).show();//("Select Item");//Set error to last Radio button
                }*/
                if(selectedId==R.id.yes){
                    DataSharing dataSharing=new DataSharing(questionnaire1.this);
                    dataSharing.add("restaurant","yes");

                }
                else{
                    DataSharing dataSharing=new DataSharing(questionnaire1.this);
                    dataSharing.add("restaurant","No");


                    // response="no";
                }
                Intent intent=new Intent(questionnaire1.this,questionnaire2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire1);
        init();
    }
}
