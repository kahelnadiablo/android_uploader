package com.kahel.uploader;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import kahel.project.uploader.UploadActivity;

public class MainActivity extends Activity {

    Button upload;

    TextView text_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        upload = (Button) findViewById(R.id.btn_upload);
        text_sample = (TextView) findViewById(R.id.textView);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*problem would be if you are passing the path of the file itself as String to your webservice*/
                /*url is reserved*/

                /*first create a hashmap for upload*/
                HashMap params = new HashMap<String,String>();
                    params.put("first_name","Name");
                    params.put("last_name","/Lupogi/slashed!");
                    params.put("file","/mnt/sdcard/Download/12py7.jpg");
                    params.put("url","http://192.168.1.4/testing_response.php");

                /*then call this Activity passing the parameters*/
                UploadActivity upload = new UploadActivity();
                upload.initializeUploader(params);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
