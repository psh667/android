package com.androidbook.stockquoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public  class  StockQuoteService extends Service
{
	private static  final String TAG  = "StockQuoteService";
	public class StockQuoteServiceImpl extends IStockQuoteService.Stub
	{
		@Override
		public double   getQuote(String ticker) throws   RemoteException
		{
			Log.v(TAG,  "¡æ∏Ò " + ticker + "ø° ¥Î«ÿ getQuote() »£√‚µ ");
			return 20.0;
		}
	}
	
	@Override
	public void  onCreate()  {
		super.onCreate();
		Log.v(TAG,  "onCreate() »£√‚µ ");
	}
	
	@Override
	public void  onDestroy()
	{
		super.onDestroy();
		Log.v(TAG,  "onDestroy() »£√‚µ ");
	}
	
	@Override
	public void  onStart(Intent intent, int startId)  {
		super.onStart(intent, startId);
		Log.v(TAG,  "onStart() »£√‚µ ");
	}
	
	@Override
	public  IBinder onBind(Intent intent)
	{
		Log.v(TAG,  "onBind() »£√‚µ ");
		return new StockQuoteServiceImpl();
	}
}
