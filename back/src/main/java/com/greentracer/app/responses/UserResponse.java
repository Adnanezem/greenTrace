package com.greentracer.app.responses;

import com.greentracer.app.models.User;

/**
 * Modèle de réponse pour renvoyer un utilisateur.
 */
public class UserResponse extends GreenTracerResponse {

    private User user;

    public UserResponse(String message, Integer status, User user) {
        super(message, status);
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
