package kahel.project.uploader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.kahel.uploader.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by kahel on 8/19/13.
 */
public class UploadActivity extends Activity {

    public Map hash_values = new HashMap();
    public String response;
    InputStream is = null;
    StringBuilder string_builder = null;

    public void initializeUploader(HashMap<String,String> values){
        hash_values.putAll(values);
        new UploaderTask().execute();
    }

    public class UploaderTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... v) {
            uploadData();
            return null;
        }

        @Override
        protected void onPreExecute() {
            /*Do something before the async task starts*/
        }
        protected void onProgressUpdate(Integer... progress) {
            /*Do some progress updater here exanple: progress bar progress, etc.*/
        }
        protected void onPostExecute(Void v) {
            /*Do something after the task is complete*/
            Log.v("Result",response);
        }
    }

    private void uploadData() {
        response = "No response";
        String url = hash_values.get("url").toString().replace(" ", "%20"); //get the URL replacing the space with %20

        //If the user is trying to upload a file use this part
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                /*This will convert the hashMap sent into individual part per key per value*/
                Set set = hash_values.entrySet();
                Iterator iterator = set.iterator();

                /*do a loop passing all the data on a string*/
                while(iterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry)iterator.next();
                    String keyword = String.valueOf(mapEntry.getKey());
                    String value = String.valueOf(mapEntry.getValue());

                    /*this will check if the passed data is a URL, file or a simple value*/
                    if(!keyword.equals("url")){
                        if(value.matches("(.*)/(.*)")){
                            File file = new File(value);
                            Log.v("Does this exists?",String.valueOf(file.exists()));
                            if(file.exists()){
                                FileBody upload_file;
                                upload_file = new FileBody(file);
                                /*not url but file*/
                                mpEntity.addPart(keyword, upload_file);
                            }else{
                                /*not url and not file*/
                                mpEntity.addPart(keyword, new StringBody(value));
                            }
                        }else{
                            /*not URL and not file*/
                            mpEntity.addPart(keyword, new StringBody(value));
                        }
                    }
                }

                post.setEntity(mpEntity);
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();

                is = resEntity.getContent();
            } catch (Exception e) {
                e.printStackTrace();
                response = "There was a problem with your upload.";
            }

        /*convert JSON to string*/
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            string_builder = new StringBuilder();
            String line = "0";

            while ((line = reader.readLine()) != null) {
                string_builder.append(line + "\n");
            }
            is.close();
            response = string_builder.toString();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
