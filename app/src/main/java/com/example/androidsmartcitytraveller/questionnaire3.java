package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class questionnaire3 extends AppCompatActivity {

    public Button but3;
    public RadioGroup radioGroup;

    public void init3()
    {
        but3 = (Button) findViewById(R.id.subQuestion3);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int selectedId = radioGroup.getCheckedRadioButtonId();
               /* if(radioGroup.getCheckedRadioButtonId()==-1){//Grp is your radio group object
                    Toast.makeText(questionnaire3.this, "Select Option", Toast.LENGTH_SHORT).show();//("Select Item");//Set error to last Radio button
                }*/

                if(selectedId==R.id.shop_yes){
                    DataSharing dataSharing=new DataSharing(questionnaire3.this);
                    dataSharing.add("shopping_mall","yes");

                }
                else {
                    DataSharing dataSharing = new DataSharing(questionnaire3.this);
                    dataSharing.add("shopping_mall", "No");

                }

                Intent intent=new Intent(questionnaire3.this,MapsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


                     }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire3);
        init3();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
