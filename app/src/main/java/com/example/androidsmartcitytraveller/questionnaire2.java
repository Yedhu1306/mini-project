package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class questionnaire2 extends AppCompatActivity {

    public Button but2;
    public RadioGroup radioGroup;

    public void init2()
    {
        but2= (Button)findViewById(R.id.subQuestion2);

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
               /* if(radioGroup.getCheckedRadioButtonId()==-1){//Grp is your radio group object
                    Toast.makeText(questionnaire2.this, "Select Option", Toast.LENGTH_SHORT).show();//("Select Item");//Set error to last Radio button
                }*/


                if(selectedId==R.id.amuse_yes){
                    DataSharing dataSharing=new DataSharing(questionnaire2.this);
                    dataSharing.add("amusement_park","yes");

                }
                else {
                    DataSharing dataSharing = new DataSharing(questionnaire2.this);
                    dataSharing.add("amusement_park", "No");

                }
                Intent int2 = new Intent(questionnaire2.this,questionnaire3.class);
                startActivity(int2);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);



            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire2);
        init2();
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup3);
      //  int selectedid=

    }
}
