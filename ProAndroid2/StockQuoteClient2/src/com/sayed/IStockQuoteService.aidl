package com.sayed;
import com.sayed.Person;
interface IStockQuoteService
{
	String getQuote(in String ticker, in Person requester);
}
