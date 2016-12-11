package com.example.administrator.getxml;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;
    private List<News> newsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);

        initListData();
    }
    private void initListData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://www.w3schools.com/xml/simplexsl.xml";
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = 0;
                    code = conn.getResponseCode();
                    if(code==200) {
                        InputStream in = conn.getInputStream();
                        newsLists = XmlParserUtils.parserXml(in);
                        System.out.println("newLists======="+newsLists.size());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv.setAdapter(new MyAdapter());
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return newsLists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView==null){
                view = View.inflate(getApplicationContext(),R.layout.item,null);
            }else{
                view = convertView;
            }
            TextView tv_name =(TextView) view.findViewById(R.id.tv_name);
            TextView tv_price =(TextView) view.findViewById(R.id.tv_price);
            TextView tv_description =(TextView) view.findViewById(R.id.tv_description);
            TextView tv_calories =(TextView) view.findViewById(R.id.tv_calories);

            tv_name.setText(newsLists.get(position).getName());
            tv_price.setText(newsLists.get(position).getPrice());
            tv_description.setText(newsLists.get(position).getDescription());
            tv_calories.setText(newsLists.get(position).getCalories());
            return view;
        }
    }
    }

