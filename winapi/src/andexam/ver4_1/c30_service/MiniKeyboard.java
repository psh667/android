package andexam.ver4_1.c30_service;

import android.content.*;
import android.inputmethodservice.*;

public class MiniKeyboard extends Keyboard {
	public MiniKeyboard(Context context, int xmlLayoutResId) {
		super(context, xmlLayoutResId);
	}

	public MiniKeyboard(Context context, int layoutTemplateResId, 
			CharSequence characters, int columns, int horizontalPadding) {
		super(context, layoutTemplateResId, characters, columns, horizontalPadding);
	}
}
