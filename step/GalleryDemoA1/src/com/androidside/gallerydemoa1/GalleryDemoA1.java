package com.androidside.gallerydemoa1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryDemoA1 extends Activity {
    //Gallery에서 보여줄 이미지를 지정한다.
    //이미지들은 /res/drawable-hdpi 아래에 pic1.jpg, pic2.jpg 와 같은 이름으로 저장한다.
    //이미지 이름은 숫자로 시작해서는 안되며 영문자, 숫자, _ 문자로 구성해야 한다.
    Integer[] images = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                         R.drawable.pic4, R.drawable.pic5, R.drawable.pic6 };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // /res/layout/gallerydemo1.xml에 선언한 gallery1라는 이름을 가진 Gallery를 불러온다.
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);

        //갤러리에서 보여줄 이미지를 처리하는 어댑터를 설정한다.
        //ImageAdapter는 BaseAdapter를 확장해서 만들어야 한다.
        gallery.setAdapter(new ImageAdapter(this));
    }

    //갤러리에 보여질 이미지에 대한 설정을 한다.
    public class ImageAdapter extends BaseAdapter {
        private Context context;
    
        public ImageAdapter(Context c) {
            context = c;
        }
    
        //이미지 전체 개수를 반환한다.
        public int getCount() {
            return images.length;
        }
    
        //주어진 position에 대한 이미지 리소스 아이디를 반환한다.
        public Integer getItem(int position) {
            return images[position];
        }
    
        //주어진 position에 대한 이미지 리소스 아이디를 반환한다.        
        public long getItemId(int position) {
            return position;
        }
    
        //주어진 position 에 대한 ImageView를 반환한다.
        public View getView(int position, View convertView, ViewGroup parent) {
            //ImageView를 생성한다.
            ImageView imageView = new ImageView(context);
            
            //주어진 position에 해당하는 이미지 리소스 아이드를 설정한다.
            imageView.setImageResource(getItem(position));
            //imageView.setAdjustViewBounds(true);
            
            //갤러리에 보여지는 이미지 크기를 설정한다. 100*80
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 80));
                        
            //ImageView를 반환한다.
            return imageView;
        }
    } 
}