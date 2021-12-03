/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pojos.users.Role;
import db.interfaces.UserManager;
import pojos.users.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JPAUserManager implements UserManager {

    private EntityManager em;

    @Override
    public void connect() {
        em = Persistence.createEntityManagerFactory("TelemedicinaPU").createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
        em.getTransaction().commit();
        List<Role> existingRoles = this.getRoles();
        /*if (existingRoles.size() < 2) {

            this.createRole(new Role("doctor"));
            this.createRole(new Role("patient"));

        }*/
    }

    @Override
    public void disconnect() {
        em.close();
    }

    @Override
    public void createUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void createRole(Role role) {
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
    }

    @Override
    public Role getRole(int id) {
        Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
        q.setParameter(1, id);
        Role role = (Role) q.getSingleResult();
        return role;

    }

    @Override
    public List<Role> getRoles() {
        Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
        List<Role> roles = (List<Role>) q.getResultList();
        return roles;
    }

    @Override
    public User checkPassword(String email, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] hash = md.digest();
            Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ?", User.class);
            q.setParameter(1, email);
            q.setParameter(2, hash);
            return (User) q.getSingleResult();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoResultException nre) {
            return null;
        }
        return null;
    }

    @Override
    public String updateUsername(String username) {
        System.out.println(username);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Query q1 = em.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
        q1.setParameter(1, username);
        User user = (User) q1.getSingleResult();
        System.out.println(user);
        System.out.print("Type your new username:");
        String newName = "";
        try {
                newName = reader.readLine();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        // Begin transaction
        em.getTransaction().begin();
        // Make changes
        user.setName_user(newName);
        // End transaction
        em.getTransaction().commit();
        return newName;
   
    }

    @Override
    public void updatePassword(String username) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Query q2 = em.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
        q2.setParameter(1, username);
        User user = (User) q2.getSingleResult();
        System.out.print("Type your new password:");
        String newPassword = "";
        try {
                newPassword = reader.readLine();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        MessageDigest md = null;
        try {
                md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        md.update(newPassword.getBytes());
        byte[] hash = md.digest();
        // Begin transaction
        em.getTransaction().begin();
        // Make changes
        user.setPassword(hash);
        // End transaction
        em.getTransaction().commit();

    }
    
    public void deletePatient(String name) {
        Query q2 = em.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
        System.out.println(name);
        q2.setParameter(1, name);
        User poorGuy = (User) q2.getSingleResult();
        // Begin transaction
        em.getTransaction().begin();
        // Store the object
        em.remove(poorGuy);
        // End transaction
        em.getTransaction().commit();
	}
}
