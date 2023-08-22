package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++) {
            if(account == accountDAO.getAllAccounts().get(i) 
            || account.getUsername().isBlank() 
            || account.getPassword().length() < 4) {
                return null;
            }
        }
        return accountDAO.addAccount(account);
    }

    public Account verifyLogin(Account account) {
        if(accountDAO.verifyLogin(account) != account){
            return accountDAO.verifyLogin(account);
        }
        return null;
    }

    //Should return an account object if the username and password match a record in the database
    public Account getAccountById(int account_id) {
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++) {
            if(account_id == accountDAO.getAllAccounts().get(i).getAccount_id()) {
                return accountDAO.getAllAccounts().get(i);
            }
        }
        return null;
    }

    //return a List of all Usernames
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    //return a Username if it exists in the database
    public Account getAccountByUsername(String username) {
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++) {
            if(username == accountDAO.getAllAccounts().get(i).getUsername()) {
                return accountDAO.getAllAccounts().get(i);
            }
        }
        return null;
    }

    //return true if the username exists in the database
    public boolean checkUsername(String username) {
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++) {
            if(username == accountDAO.getAllAccounts().get(i).getUsername()) {
                return true;
            }
        }
        return false;
    }

}
