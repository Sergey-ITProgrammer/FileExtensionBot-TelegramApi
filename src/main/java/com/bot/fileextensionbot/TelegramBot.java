package com.bot.fileextensionbot;

import com.bot.fileextensionbot.handler.FileHandler;
import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Setter
public class TelegramBot extends TelegramWebhookBot {
    private String botUserName;
    private String botToken;
    private String botPath;
    private FileHandler fileHandler;

    public TelegramBot(DefaultBotOptions options, FileHandler fileHandler) {
        super(options);
        this.fileHandler = fileHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        String replyText = fileHandler.getFileExtension(update, getBotToken());

        SendMessage sendMessage = new SendMessage(String.valueOf(update.getMessage().getFrom().getId()), replyText);
        sendMessage.enableMarkdown(true);

        return sendMessage;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
