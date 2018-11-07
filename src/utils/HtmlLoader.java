package utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by ccoders on 04.02.17.
 */
public class HtmlLoader {
    public String getHTML(String urlToRead) {
        URL url;
        String html = "";

        try {
            url = new URL(urlToRead);

            LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            System.out.println(url);

            String string = reader.readLine();
            while (string != null) {
                html += string + "\n";
                string = reader.readLine();
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return html;
    }

    public void loadFileYML(String urlS, String localFileName){


        File file = new File(localFileName);

        try {

            URL url = new URL(urlS);
            if(file.exists()){
                file.delete();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println(url);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            String line;
            boolean f = true;
            while((line = reader.readLine()) != null){
                if(f){
                    f=false;
                    continue;
                }

                //System.out.println(line);
                writer.write(line+"\n");
            }
            writer.flush();
            writer.close();
            reader.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void loadFile(String localFile , String urlFile){

        FilesUtil.saveImage(urlFile,localFile);

    }

    public String postHTML(String targetURL, String urlParameters) {
        String result = "";
        try {
            URL url = new URL(targetURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(URLEncoder.encode(urlParameters, StandardCharsets.UTF_8));

            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            wr.close();
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
