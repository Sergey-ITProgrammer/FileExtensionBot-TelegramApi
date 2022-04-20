package com.bot.fileextensionbot.config;

import com.bot.fileextensionbot.TelegramBot;
import com.bot.fileextensionbot.handler.FileHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class BotConfig {
    private String botUserName;
    private String botToken;
    private String botPath;

    @Bean
    public TelegramBot TelegramBot(FileHandler handler) {
        DefaultBotOptions options = new DefaultBotOptions();

        TelegramBot telegramBot = new TelegramBot(options, handler);

        telegramBot.setBotToken(botToken);
        telegramBot.setBotPath(botPath);
        telegramBot.setBotUserName(botUserName);

        return telegramBot;
    }
}
