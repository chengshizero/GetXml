package com.example.administrator.getxml;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class XmlParserUtils {
    public static List<News> parserXml(InputStream in) throws XmlPullParserException, IOException {
        List<News> newsLists = null;
        News news =null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(in,"utf-8");
        int type = parser.getEventType();

        while (type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("breakfast_menu".equals(parser.getName())){
                        newsLists = new ArrayList<News>();
                    }else if("food".equals(parser.getName())){
                        news = new News();
                    }else if("name".equals(parser.getName())){
                        news.setName(parser.nextText());
                    }else if("price".equals(parser.getName())){
                        news.setPrice(parser.nextText());
                    }else if("description".equals(parser.getName())){
                        news.setDescription(parser.nextText());
                    }else if("calories".equals(parser.getName())){
                        news.setCalories(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("food".equals(parser.getName())) {
                        newsLists.add(news);
                    }
                    break;
            }
            type = parser.next();
        }

        return newsLists;
    }
}
