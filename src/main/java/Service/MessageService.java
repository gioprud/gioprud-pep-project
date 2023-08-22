package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().length() < 255 
        && message.getPosted_by() != 0 
        && message.getMessage_text().length() > 0) {
            return messageDAO.createNewMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();

    }

    public Message getMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        return message;
    }

    public Message deleteMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        return message;
    }

    public Message updateMessageById(int message_id, Message message) {

        // check to see if it exists first
        if (messageDAO.getMessageById(message_id).getMessage_text().isBlank()) {
            return null;
        }

        // updates message if it exists and meets constraints
        messageDAO.updateMessageById(message_id, message);

        if (message.getMessage_text().length() >= 255 
            || message.getMessage_text().isBlank()) {
                return null;
        } else {
            
            return messageDAO.getMessageById(message_id);
        }
        
    }

    public List<Message> getAllMessagesByUserId(int account_id) {
        List<Message> messages = messageDAO.getAllMessagesByUserId(account_id);
        System.out.println(messages);
        return messages;
    }
    
}
