package io.proj3ct.GeekSuperPuperBot.service;

public class Jokes {
    private static final String[] jokeForTelegram = {
            "Гроб карлика-оптимиста наполовину полон.",
            "У каких верблюдов три горба? У беременных!!!",
            "Бабка переходила не на тот свет, а попала на тот >=)",
            "Что общего между между хлебом и евреем? Время запекания!!! Парам пам)",
            "«Иду себе иду, никого не трогаю...» - так начинается каждый рассказ безрукого мальчика.",
            "У семьи каннибалов умер родственник. И грустно и вкусно.",
            "Среди детей антипрививочников самое популярное имя - Любовь. Потому что Любовь живет три года.",
            "Я копал яму в саду, как вдруг откопал целый сундук с золотом. Я уже было побежал домой, чтобы рассказать жене о ценной находке. Потом вспомнил, зачем я вообще копал яму.",
            "50-летняя Елена вышла замуж по расчету, но не подрасчитала и умерла раньше.",
            "Шутки про утопленников обычно несмешные, потому что лежат на поверхности.",
            "На похоронах успопшего так хвалили, что вдова два раза подходила к гробу, чтобы посмотреть кто там лежит.",
            "— Мы его теряем! Сестра, быстро, де... дефе... дерб... дерф... дефербля... дефре... деберля... дляфебриля... Время смерти 18:06.",
            "Коронавирус Covid-2019 очень хорошо передается через деньги. Вот почему он так быстро распространился по Европе, и почему его так мало в России",
            "Чувство черного юмора - как ноги. У кого-то есть, а у кого-то нет."
    };
    static String joke(){
        int n = (int)(Math.random()*(jokeForTelegram.length));
        String text = jokeForTelegram[n];
        return text;
    }
}