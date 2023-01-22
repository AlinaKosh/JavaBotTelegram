package io.proj3ct.GeekSuperPuperBot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Weather {


    public String getWeather() throws Exception {

        Document doc = Jsoup.connect("https://world-weather.ru/").get();

        String nameOfSite = doc.title();

        String area = doc.getElementsByClass("list-item-city").text();
        String temp = doc.getElementsByClass("temperature").text();
        String[] arrArea = area.split(" ");
        String[] arrTemp = temp.split(" ");
        String res = "";
        for (int i = 0; i < arrArea.length - 2; i++) {
            res += (arrArea[i] + " " + arrTemp[i]) + "\n";
        }

        return "Название сайта: " + nameOfSite + "\n\n" + "Информация актуальна на сегодняшний день." + "\n\n" + res;
    }


}
