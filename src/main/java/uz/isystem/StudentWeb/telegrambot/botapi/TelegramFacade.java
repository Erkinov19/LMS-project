package uz.isystem.StudentWeb.telegrambot.botapi;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.isystem.StudentWeb.telegrambot.cache.BotState;
import uz.isystem.StudentWeb.telegrambot.cache.BotStateContext;
import uz.isystem.StudentWeb.telegrambot.cache.UserDataCache;
import uz.isystem.StudentWeb.telegrambot.utill.CurrentMessage;

@Service
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDateCache;


    public TelegramFacade(BotStateContext botStateContext,
                          UserDataCache userDateCache) {
        this.botStateContext = botStateContext;
        this.userDateCache = userDateCache;
    }


    public CurrentMessage handle(Update update) {
        if (update.hasMessage()) {
            return handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            return handleCallBack(update.getCallbackQuery());
        } else return null;
    }

    private CurrentMessage handleMessage(Message message) {
        long userId = message.getFrom().getId();
        BotState state;

        if (message.hasContact()) {
            message.setText(message.getContact().getPhoneNumber());
        }
        String inputText = message.getText();

        switch (inputText) {
            case "/start":
                state = BotState.SHOW_MAIN_MENU;
                break;
            case "/help":
                state = BotState.SHOW_HELP_MENU;
                break;
            case "Login":
                state = BotState.LOGIN;
                break;
            case "About":
                state = BotState.SHOW_ABOUT;
                break;
            case "Info":
                state = BotState.SHOW_ABOUT;
                break;
            default:
                state = userDateCache.getUserCurrentBotState(userId);
                break;
        }
        userDateCache.setUserCurrentBotState(userId,state);

        return botStateContext.processInputMessage(state, message);
    }
    private void activeUserHandler(Message message){
        long userId = message.getFrom().getId();
        BotState state;
    }
    private CurrentMessage handleCallBack(CallbackQuery callbackQuery){
        return null;
    }
}

