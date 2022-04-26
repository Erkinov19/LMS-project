package uz.isystem.StudentWeb.telegrambot.botapi.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.isystem.StudentWeb.telegrambot.botapi.InputMessageHandler;
import uz.isystem.StudentWeb.telegrambot.cache.BotState;
import uz.isystem.StudentWeb.telegrambot.servicee.ReplyMessageService;
import uz.isystem.StudentWeb.telegrambot.utill.CurrentMessage;
import uz.isystem.StudentWeb.telegrambot.utill.MessageType;

@Component
public class InfoHandler implements InputMessageHandler {
    @Autowired
    private ReplyMessageService replyMessageService;

    @Override
    public CurrentMessage handle(Message message) {
        SendMessage sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.info");
        CurrentMessage currentMessage = new CurrentMessage();
        currentMessage.setSendMessage(sendMessage);
        currentMessage.setType(MessageType.SEND_MESSAGE);
        return currentMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_INFO;
    }
}

