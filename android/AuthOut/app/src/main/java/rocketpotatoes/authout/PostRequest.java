package rocketpotatoes.authout;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequest extends AsyncTask <String, String, String> {

    private static final int URL_INDEX = 0;
    private static final int DATA_INDEX = 0;

    public PostRequest() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        String urlString = params[URL_INDEX];
        String data = params[DATA_INDEX];
        OutputStream outputStream;

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            outputStream = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream,"UTF-8"));

            writer.write(data);
            writer.flush();
            writer.close();
            outputStream.close();

            urlConnection.connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
