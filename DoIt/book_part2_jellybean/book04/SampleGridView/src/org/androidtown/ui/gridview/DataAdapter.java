package org.androidtown.ui.gridview;

import org.ubiworks.mobile.protocol.mdbc.android.MTable;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter {

	Context mContext;

	private MTable mTable;

	public static int oddColor = Color.rgb(225, 225, 225);
	public static int headColor = Color.rgb(12, 32, 158);

	private int selectedRow = -1;

	public DataAdapter(Context context) {
		super();

		mContext = context;
	}

	public DataAdapter(Context context, MTable table) {
		super();

		mContext = context;
		setTable(table);
	}

	public int getNumColumns() {
		if (mTable == null) {
			return 0;
		}
		return mTable.countColumn;
	}

	public int getCount() {
		if (mTable == null) {
			return 0;
		}
		return (mTable.count()+1) * mTable.countColumn;
	}

	public Object getItem(int position) {
		if (mTable == null) {
			return null;
		}

		if (position < mTable.countColumn) {
			if (mTable.columns != null && mTable.columns.length > position) {
				return mTable.columns[position];
			} else {
				return null;
			}
		}

		// calculate row and column
		int rowIndex = position / mTable.count();
		int columnIndex = position % mTable.count();

		Object item = null;
		if (rowIndex-1 < mTable.count() && columnIndex < mTable.countColumn) {
			try {
				item = mTable.getCell(rowIndex, columnIndex);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		return item;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup group) {
		Log.d("DataAdapter", "getView(" + position + ") called.");

		if (mTable == null) {
			return null;
		}

		// create a params





		// calculate row and column
		int rowIndex = position / mTable.countColumn;
		int columnIndex = position % mTable.countColumn;

		Log.d("DataAdapter", "Index : " + rowIndex + ", " + columnIndex);


		// create a Layout Container
		LinearLayout container = new LinearLayout(mContext);
		container.setOrientation(LinearLayout.VERTICAL);

		View topLine = new View(mContext);
		topLine.setBackgroundColor(Color.LTGRAY);

		LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		topParams.height = 1;

		container.addView(topLine, topParams);


		LinearLayout itemContainer = new LinearLayout(mContext);
		itemContainer.setOrientation(LinearLayout.HORIZONTAL);

		View leftLine = new View(mContext);
		leftLine.setBackgroundColor(Color.LTGRAY);

		RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		leftParams.width = 1;

		itemContainer.addView(leftLine, leftParams);


		// create a TextView
		TextView itemView = new TextView(mContext);
		itemView.setBackgroundColor(Color.TRANSPARENT);

		RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				100);
		itemParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		itemContainer.addView(itemView, itemParams);


		View rightLine = new View(mContext);
		rightLine.setBackgroundColor(Color.LTGRAY);

		RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rightParams.width = 1;

		itemContainer.addView(rightLine, rightParams);


		LinearLayout.LayoutParams itemContainerParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		container.addView(itemContainer, itemContainerParams);



		View bottomLine = new View(mContext);
		bottomLine.setBackgroundColor(Color.LTGRAY);

		LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		bottomParams.height = 1;

		container.addView(bottomLine, bottomParams);




		// set text
		if (position < mTable.countColumn) {
			itemView.setText(mTable.columns[columnIndex].name);
		} else {
			Object item = "";
			try {
				item = mTable.getCell(rowIndex-1, columnIndex);
			} catch(Exception ex) {
				ex.printStackTrace();
			}

			itemView.setText(item.toString());
		}

		// set default gravity
		itemView.setGravity(Gravity.LEFT);

		// set default foreground color
		itemView.setTextColor(Color.BLACK);

		// set background color
		if (rowIndex == 0) {
			itemView.setTextColor(Color.WHITE);
			itemView.setBackgroundColor(headColor);
			itemView.setGravity(Gravity.CENTER);
        } else if (rowIndex == getSelectedRow()) {
        	itemView.setBackgroundColor(Color.YELLOW);
        } else if ((rowIndex & 1) == 0) {
        	itemView.setBackgroundColor(oddColor);
        } else {
        	itemView.setBackgroundColor(Color.WHITE);
        }



		return container;
	}

	/**
	 * set MTable instance
	 *
	 * @param mTable
	 */
	public void setTable(MTable mTable) {
		this.mTable = mTable;

		notifyDataSetChanged();
	}

	/**
	 * get MTable instance
	 *
	 * @return
	 */
	public MTable getTable() {
		return mTable;
	}

	/**
	 * set selected row
	 *
	 * @param selectedRow
	 */
	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * get selected row
	 *
	 * @return
	 */
	public int getSelectedRow() {
		return selectedRow;
	}


}
