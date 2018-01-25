package org.thinway.lectorrss;

import android.util.Log;

import org.thinway.lectorrss.model.News;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by fdelgado on 18/1/18.
 */

public class ParseNews {

    private static final String TAG = "ParseNews";

    private String xmlData;
    private ArrayList<News> news;

    public ParseNews(String xmlData) {
        this.xmlData = xmlData;
        news = new ArrayList<>();
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public boolean process() {
        boolean status = true;
        News currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){

                String tagName = xpp.getName();

                switch ( eventType ) {
                    case XmlPullParser.START_TAG:
                        // Si entro aquí quiere decir que entramos en un nuevo item
                        // es decir, una nueva noticia
                        if(tagName.equalsIgnoreCase("item")){
                            inEntry = true;
                            currentRecord = new News();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry) {
                            if(tagName.equalsIgnoreCase("item")){
                                news.add(currentRecord);
                                inEntry = false;
                            } else if ( tagName.equalsIgnoreCase("title")){
                                currentRecord.setTitle(textValue);
                            } else if ( tagName.equalsIgnoreCase("description")){
                                currentRecord.setDescription(textValue);
                            }
                        }
                        break;
                    default:
                        // Nothing
                }

                eventType = xpp.next();
            }
        }catch (Exception e) {
            Log.d(TAG, "Problema parseando el RSS" + e.getMessage());
            e.printStackTrace();
            status = false;
        }

//        for(News noticia: news){
//            Log.d(TAG, "Noticia: " + noticia.getTitle());
//        }

        return status;
    }
}















