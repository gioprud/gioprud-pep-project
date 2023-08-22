package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{messageId}", this::getMessageIdHandler);
        app.delete("/messages/{messageId}", this::deleteMessageIdHandler);
        app.patch("/messages/{messageId}", this::patchMessageIdHandler);
        app.get("/accounts/{accountId}/messages", this::getAllMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if (newAccount == null) {
            context.status(400);
        }else {
            context.status(200);
            context.json(newAccount);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginCheck = accountService.verifyLogin(account);
        if (loginCheck == null) {
            context.status(401);
        }else {
            context.status(200);
            context.json(loginCheck);
        }
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null) {
            context.status(400);
        }else {
            context.status(200);
            context.json(newMessage);
        }

    }

    private void getMessagesHandler(Context context) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        context.status(200);
        context.json(messages);
        
    }

    private void getMessageIdHandler(Context context) throws JsonProcessingException {
        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("messageId")));
        if (message.getMessage_text().isBlank()){
            context.status(200);
        } else {
            context.status(200);
            context.json(message);
        }
        
    }
    // Delete message by id
    private void deleteMessageIdHandler(Context context) throws JsonProcessingException {
        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("messageId")));
        if (message.getMessage_text().isBlank()){
            context.status(200);
        } else {
            messageService.deleteMessageById(Integer.parseInt(context.pathParam("messageId")));
            context.status(200);
            context.json(message);
        }
    }

    private void patchMessageIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.updateMessageById(Integer.parseInt(context.pathParam("messageId")), message);
        if (newMessage == null){
            context.status(400);
        } else {
            context.status(200);
            context.json(newMessage);
        }
        
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessagesByUserId(Integer.parseInt(context.pathParam("accountId")));
        if (messages.isEmpty() == false){
            context.status(200);
            context.json(messages);
        } else {
            context.status(200);
            context.json(messages);
        }
        
    }

}