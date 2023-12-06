package com.example.eioderzwei.server;
/**
 * Wenn sich ein Spieler registriert, dann bekommt dieser eine einzigartige Benutzerkennung (UserIdent)
 */
public class RegisteredPlayer extends Bot {
    private final String userIdent;

    public RegisteredPlayer(Player player, String userId){
        super(player.getUsername(), player.getPassword());
        this.userIdent = userId;
    }
}
