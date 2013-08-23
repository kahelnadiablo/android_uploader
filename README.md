Android Uploader
================

Simple uploader for android.

How to use:

Step 1. Copy and paste the package kahe.project.uploader together with its content then import it on your Activity

Step 2. Create a hashmap that will contain all the key and values you need. This should be a String

        sample:
          
          HashMap params = new HashMap<String,String>();
            params.put("first_name","Your Name");
            params.put("last_name","Your Last Name");
            params.put("file","/file_uri/file.ext");
            params.put("url", "http://www.sample.com/index/");
            
Step 3: Call the uploader Activity

        UploadActivity upload = new UploadActivity();
                upload.initializeUploader(params);
                
Take note that you need to add the following JAR files in your libs folder:

apache-mime4j-0.6.1, httpclient-4.1, httpcore-4.1, httpmime-4.1
                
This will automatically check if the parameter is a file, string or url. but take note that the url keyword is reserved.

Feel free to modify it depending on your needs.

Happy Coding! :)
