package io.proj3ct.GeekSuperPuperBot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Currency {
    private String currencyUSD;
    private String currencyEUR;
    private String date;
    private String inflation;
    private String targetForInflation;


    public String getCurrency() throws Exception {
            Document doc = Jsoup.connect("https://cbr.ru/").get();


            date = doc.getElementsByClass("col-md-2 col-xs-7 _right").last().text();
            currencyUSD = doc.getElementsByClass("col-md-2 col-xs-9 _dollar").last().text() + " - " + doc.getElementsByClass("col-md-2 col-xs-9 _right mono-num").last().text();

            currencyEUR = doc.getElementsByClass("col-md-2 col-xs-9 _euro").last().text() + " - " + doc.getElementsByClass("col-md-2 col-xs-9 _right mono-num").first().text();


            inflation = doc.getElementsByClass("main-indicator_value").first().text();
            String dateInflation = doc.getElementsByClass("main-indicator_text").text() + " - " + inflation;

        return "Название сайта: " + doc.title() + "\n" + date + " - " + currencyUSD + "\n" + date + " - " + currencyEUR +"\n" + dateInflation ;

    }
}
