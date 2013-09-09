package org.androidtown.multimemo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemoListItemView extends LinearLayout {

	private ImageView itemPhoto;

	private TextView itemDate;

	private TextView itemText;

	private ImageView itemVideoState;

	private ImageView itemVoiceState;

	private ImageView itemHandwriting;


	public MemoListItemView(Context context) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.memo_listitem, this, true);

		itemPhoto = (ImageView) findViewById(R.id.itemPhoto);

		itemDate = (TextView) findViewById(R.id.itemDate);

		itemText = (TextView) findViewById(R.id.itemText);

		itemVideoState = (ImageView) findViewById(R.id.itemVideoState);

		itemVoiceState = (ImageView) findViewById(R.id.itemVoiceState);

		itemHandwriting = (ImageView) findViewById(R.id.itemHandwriting);
	}

	public void setContents(int index, String data) {
		if (index == 0) {
			itemDate.setText(data);
		} else if (index == 1) {
			itemText.setText(data);
		} else if (index == 2) {
			if (data == null || data.equals("-1") || data.equals("")) {
				itemHandwriting.setImageBitmap(null);
			} else {
				itemHandwriting.setImageURI(Uri.parse(BasicInfo.FOLDER_PHOTO + data));
			}
		} else if (index == 3) {
			if (data == null || data.equals("-1") || data.equals("")) {
				itemPhoto.setImageResource(R.drawable.person);
			} else {
				itemPhoto.setImageURI(Uri.parse(BasicInfo.FOLDER_PHOTO + data));
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setMediaState(Object sVideo, Object sVoice) {
		if(sVideo == null) {
			itemVideoState.setImageResource(R.drawable.icon_video_empty);
		} else {
			itemVideoState.setImageResource(R.drawable.icon_video);
		}

		if(sVoice == null) {
			itemVoiceState.setImageResource(R.drawable.icon_voice_empty);
		} else {
			itemVoiceState.setImageResource(R.drawable.icon_voice);
		}
	}

}
