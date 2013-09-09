package com.androidside.gridviewdemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewDemoA1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //GridView 객체를 main.xml을 기술된 속성을 기준으로 생성한다. 
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        //GridView에 이미지를 보여주기 위한 이미지 어댑터를 설정한다.
        gridview.setAdapter(new ImageAdapter(this));
    }

    //BaseAdapter를 상속하여 GridView에 이미지를 보여줄 수 있는 기능을 정의한다.
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        
        //GridView로 보여주기 위한 이미지 배열을 선언한다.
        private Integer[] images = { R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6, R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6, R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6, R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6 };

        public ImageAdapter(Context c) {
            this.context = c;
        }

        //전체 이미지 개수를 반환한다.
        public int getCount() {
            return images.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        //각각의 이미지를 설정한다.
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            //재사용할 수 있는 ImageView가 없다면 ImageView 객체를 새로 생성한다.
            if (convertView == null) {
                imageView = new ImageView(context);
                //이미지 뷰의 크기와 이미지 크기 그리고 공백을 설정한다.
                imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(10, 10, 10, 10);
            
            //재사용할 수 있는 ImageView가 있다면 이를 재사용한다.
            } else {
                imageView = (ImageView) convertView;
            }

            //이미지를 인자로 넘어온 position에 맞게 설정한다.
            imageView.setImageResource(images[position]);

            return imageView;
        }
    }
}
