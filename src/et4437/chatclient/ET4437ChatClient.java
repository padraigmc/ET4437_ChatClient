/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import chatwebservice.ChatWebService_Service;
import chatwebservice.ChatWebService;
import java.util.List;

/**
 *
 * @author Padraig
 */
public class ET4437ChatClient {

    private static ChatWebService chatServiceProxy;
    
    public static void main(String[] args) {
        List<String> registeredUsers;
        
        try {
            ChatWebService_Service service = new ChatWebService_Service();
            chatServiceProxy = service.getChatWebServicePort();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        registeredUsers = chatServiceProxy.getUsers(true);
        for (String user : registeredUsers)
        {
            System.out.println(user);
        }
    }
    
    
}
