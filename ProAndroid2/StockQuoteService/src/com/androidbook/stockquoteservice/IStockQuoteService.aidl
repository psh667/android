// 이 파일은 IStockQuoteService.aidl이다.
package  com.androidbook.stockquoteservice;
interface  IStockQuoteService
{
	double   getQuote(String ticker);
}
