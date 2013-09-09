package pro.android;

import android.content.Context;
import android.util.AttributeSet;

public class TrueButton extends BooleanButton {
	protected boolean myValue() {
		return true;
	}
	
	public TrueButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
