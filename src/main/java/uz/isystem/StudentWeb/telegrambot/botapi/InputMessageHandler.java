package uz.isystem.StudentWeb.telegrambot.botapi;

import org.telegram.telegrambots.meta.api.objects.Message;
import uz.isystem.StudentWeb.telegrambot.cache.BotState;
import uz.isystem.StudentWeb.telegrambot.utill.CurrentMessage;

public interface InputMessageHandler {
    CurrentMessage handle(Message message);

    BotState getHandlerName();
}
