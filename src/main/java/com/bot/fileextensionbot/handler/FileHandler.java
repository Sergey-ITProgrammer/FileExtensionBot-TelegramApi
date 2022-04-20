package com.bot.fileextensionbot.handler;

import com.bot.fileextensionbot.service.FileExtensionService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FileHandler {
    private final FileExtensionService fileExtensionService;

    public FileHandler(FileExtensionService fileExtensionService) {
        this.fileExtensionService = fileExtensionService;
    }

    public String getFileExtension(Update update, String botToken) {
        String replyMessage;

        if (update.getMessage().hasDocument()) {
            Document document = update.getMessage().getDocument();

            URL documentURL;

            try {
                documentURL = new URL("https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + document.getFileId());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            String fileJSON;

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(documentURL.openStream()));

                fileJSON = bufferedReader.readLine();

                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            JSONObject fileResponse = new JSONObject(fileJSON);
            JSONObject result = fileResponse.getJSONObject("result");
            String filePath = result.getString("file_path");

            URL fileURL;

            try {
                fileURL = new URL("https://api.telegram.org/file/bot" + botToken + "/" + filePath);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            byte[] arrayOfFileBytes = getFileBytes(fileURL);

            replyMessage = "*Intended file extensions:*" + " \n" + fileExtensionService.getFileExtension(arrayOfFileBytes);
        } else {
            replyMessage = "Sorry, this is not a file";
        }

        return replyMessage;
    }

    private byte[] getFileBytes(URL file) {
        byte[] arrayOfFileBytes;

        try {
            arrayOfFileBytes = file.openStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arrayOfFileBytes;
    }
}
