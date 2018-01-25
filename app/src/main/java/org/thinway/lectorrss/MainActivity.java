package org.thinway.lectorrss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public Button mDownloadRss;
    public ListView mListNews;

    private String mFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadRss = findViewById(R.id.downloadRss);
        mListNews = findViewById(R.id.listViewNews);

        mDownloadRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseNews parseNews = new ParseNews(mFileContent);
                parseNews.process();

//                ArrayAdapter<News> arrayAdapterNews = new ArrayAdapter<News>(
//                        MainActivity.this,
//                        R.layout.news_list_item,
//                        R.id.textViewTitle,
//                        parseNews.getNews()
//                );

                AdapterItem adapterNews = new AdapterItem(
                        MainActivity.this,
                        parseNews.getNews()
                );

                mListNews.setAdapter(adapterNews);
            }
        });

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ep00.epimg.net/rss/tags/ultimas_noticias.xml");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... strings) {
            mFileContent = downloadXmlFile(strings[0]);

            if( mFileContent == null ){
                Log.d(TAG, "Problema descargando el XML");
            }

            return mFileContent;
        }

        /**
         * Construyo un String con el contenido del archivo XML
         * indicado en la url que se pasa como parámetro.
         *
         * @param urlPath Url del recurso que se quiere parsear
         * @return
         */
        public String downloadXmlFile(String urlPath){

            //StringBuilder vs StringBuffer
            StringBuilder tempBuffer = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new char[500];

                while( true ) {
                    charsRead = isr.read(inputBuffer);

                    if( charsRead <= 0 ) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();

            }catch (IOException e){
                Log.d(TAG, "Error: No se pudo descargar el RSS - " + e.getMessage());
            }

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param result The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.d(TAG, "Resultado: " + result);
        }
    }
}
