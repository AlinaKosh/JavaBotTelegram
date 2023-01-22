package io.proj3ct.GeekSuperPuperBot.service;

import io.proj3ct.GeekSuperPuperBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    static final String HELP_TEXT = "Этот бот предназначен для демонстрации умения работать с Java и Spring\n\n" +
            "Вы можете выполнять команды из главного меню слева или набрав команду:\n\n" +
            "Введите /start, чтобы увидеть приветственное сообщение\n\n" +
            "Введите /help, чтобы снова увидеть это сообщение\n\n"+
            "Введите /joke, чтобы увидеть шутку\n\n"+
            "Введите /currency, чтобы знать курс валют\n\n"+
            "Введите /weather, чтобы узнать какая погода в вашем регионе";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","get a welcome message"));
        listOfCommands.add(new BotCommand("/help","info how to use this info"));
        listOfCommands.add(new BotCommand("/joke","happy joke"));
        listOfCommands.add(new BotCommand("/currency","currency exchange rate"));
        listOfCommands.add(new BotCommand("/weather","weather information"));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),null));
        }catch (TelegramApiException e){
            log.error("Error settings bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    //ГЛАВНЫЙ МЕТОД, где как раз информация, которая поступает от пользователя и будут тут обрабатываться
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) { //убедились, что нам что-то прислали и там точно текст

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();//у каждого пользователя есть свой ID и к боту может обращаться сразу несколько пользователей, поэтому у каждого пользователя есть собственный индификатор

            switch (messageText) {
                case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                case "/help":
                    sendMessage(chatId,HELP_TEXT);
                    break;
                case "/joke":
                    joke(chatId);
                    break;
                case "/currency":
                    currency(chatId);
                    break;
                case "/weather":
                    weather(chatId);
                    break;
                default:
                        sendMessage(chatId, "Прости, пока это не поддерживается");

            }
        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("YES_BUTTON_JOKE")){

                String text = Jokes.joke();
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            } else if (callbackData.equals("NO_BUTTON_JOKE")){
                String text = "Эхх... а ведь у меня есть такая весёлая шутка(";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }
            if (callbackData.equals("YES_BUTTON_CURRENCY")){

                Currency currency = new Currency();
                String text;
                try {
                    text = currency.getCurrency();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            } else if (callbackData.equals("NO_BUTTON_CURRENCY")){
                String text = "Эхх... а ведь полезно знать, где сейчас рубль(";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }

            if (callbackData.equals("YES_BUTTON_WEATHER")){

                Weather weather = new Weather();
                String text;
                try {
                    text = weather.getWeather();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            } else if (callbackData.equals("NO_BUTTON_WEATHER")){
                String text = "Это зря, а вдруг дождь!";
                EditMessageText message = new EditMessageText();
                message.setChatId(String.valueOf(chatId));
                message.setText(text);
                message.setMessageId((int)(messageId));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }

        }


    }

    private void joke(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Правда хотите шуточку?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();

        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON_JOKE");

        var noButton = new InlineKeyboardButton();

        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON_JOKE");

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void currency(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы точно хотите узнать курс доллара и евро?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();

        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON_CURRENCY");

        var noButton = new InlineKeyboardButton();

        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON_CURRENCY");

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void weather(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вам интересна погода на сегодня?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();

        yesButton.setText("Yes");
        yesButton.setCallbackData("YES_BUTTON_WEATHER");

        var noButton = new InlineKeyboardButton();

        noButton.setText("No");
        noButton.setCallbackData("NO_BUTTON_WEATHER");

        rowInLine.add(yesButton);
        rowInLine.add(noButton);

        rowsInLine.add(rowInLine);

        markupInline.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }


    private void startCommandReceived(long chatId, String name) {

        String answer = "Приветик, " + name + ", рад видеть тебя здесь!!!";
        log.info("Ответил пользователю " + name);

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }

    }
}
