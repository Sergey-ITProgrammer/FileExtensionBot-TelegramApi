package com.bot.fileextensionbot;

import com.bot.fileextensionbot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties({BotConfig.class})
public class FileExtensionBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileExtensionBotApplication.class, args);
	}

}
