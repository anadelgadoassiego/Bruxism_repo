/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import pojos.users.Role;
import pojos.users.User;

public interface UserManager {

    public void connect();

    public void disconnect();

    public void createUser(User user);

    public void createRole(Role role);

    public Role getRole(int id);

    public List<Role> getRoles();

    public User checkPassword(String username, String password);

    public String updateUsername(String username);

    public void updatePassword(String username);
    
    public void deletePatient(String username);

}
