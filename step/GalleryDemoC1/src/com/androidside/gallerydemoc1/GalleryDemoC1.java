package com.androidside.gallerydemoc1;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class GalleryDemoC1 extends Activity {
    private Cursor cursor;
    private int columnIndex;
    private Gallery gallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //managedQuery 메소드에서 조회할 컬럼 이름을 지정한다.
        //이미지의 썸네일 아이디를 지정한다.
        String[] proj = { MediaStore.Images.Thumbnails._ID };
        
        //외부 메모리에 있는 이미지 정보를 조회한다.        
        cursor = managedQuery(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, //외부 메모리 
                proj, //조회할 컬럼
                null, //WHERE절, 조건 지정
                null, //WHERE절, 선택 인자 지정
                null); //Order by절, 정렬 순서 지정
        startManagingCursor(cursor);

        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
        
        // /res/layout/main.xml에 선언한 gallery1이라는 이름을 가진 Gallery를 불러온다
        gallery = (Gallery) findViewById(R.id.gallery1);
        
        //갤러리에서 보여줄 이미지를 처리하는 어댑터를 설정한다.
        //ImageAdapter는 BaseAdapter를 확장해서 만들어야 한다.
        gallery.setAdapter(new ImageAdapter(this));

        //갤러리의 이미지를 클릭했을 때 해당 이미지를 크게 보여주기 위한 로직을 설정한다.
        //OnItemClickListener 객체를 갤러리에 설정하면 클릭 이벤트가 발생했을 때 원하는 처리를 할 수 있다.
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position,
                    long id) {
                //managedQuery 메소드에서 조회할 컬럼 이름을 지정한다.
                //이미지 데이터를 지정한다.
                String[] proj = { MediaStore.Images.Media.DATA };
                
                //외부 메모리에 있는 이미지 정보를 조회한다.        
                Cursor dataCursor = managedQuery(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //외부 메모리 
                        proj, //조회할 컬럼
                        null, //WHERE절, 조건 지정
                        null, //WHERE절, 선택 인자 지정
                        null); //Order by절, 정렬 순서 지정
                
                //데이터에 해당하는 컬럼 인덱스를 얻는다.
                int columnIndex = dataCursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        
                //갤러리에서 사용자가 선택한 아이템에 해당하는 위치로 커서를 이동한다.
                dataCursor.moveToPosition((int) gallery.getSelectedItemId());
        
                //지정된 인덱스에 해당하는 이미지 파일 이름을 얻는다. 
                String filename = dataCursor.getString(columnIndex);
        
                
                //파일 이름으로 비트맵을 얻는다. 
                Bitmap bitmap = BitmapFactory.decodeFile(filename);
        
                //비트맵 이미지를 이미지뷰에 설정한다.
                ImageView imageView = (ImageView) findViewById(R.id.image1);
                imageView.setImageBitmap(bitmap);
                
                dataCursor.close();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;

        public ImageAdapter(Context c) {
            context = c;

            // 갤러리를 표현하기 위한 배경 스타일을 얻는다.
            // R.styleable.Gallery1은 갤러리의 액자 모양을 지정하기 위한 스타일 이름이며
            // 이 예제에서는 /res/values/styles.xml에 작성했다.
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
            itemBackground = a.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 0);

            // 갤러리 배경 스타일을 얻기 위해 사용했던 자원을 해지한다.
            a.recycle();
        }

        public int getCount() {
            return cursor.getCount();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            
            if (convertView == null) {
                imageView = new ImageView(context);    
                
                //이미지를 아래에서 설정한 레이아웃 크기의 중앙에 배치한다.
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                
                //갤러리에 보여지는 이미지 크기를 설정한다. 100*80
                imageView.setLayoutParams(new Gallery.LayoutParams(100, 80));
                
                //ImageView 배경 스타일에 Gallery의 배경 스타일을 지정한다. 
                imageView.setBackgroundResource(itemBackground);
            } else {
                imageView = (ImageView) convertView;
            }
            
            cursor.moveToPosition(position);
                
            int id = cursor.getInt(columnIndex);
            
            //이미지 경로를 설정한다.
            imageView.setImageURI(Uri.withAppendedPath(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + id));        
            
            return imageView;
        }
    }
}