package com.example.eioderzwei.server.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.example.eioderzwei.server.Card;
import com.example.eioderzwei.server.exceptions.*;


public interface GameManagerInterface extends Remote {

    void get_map() throws RemoteException;

    void initialize_rooster(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    boolean has_game_started(String currentRoomName) throws RemoteException, RoomDoesNotExistException;
    void start(String currentRoomName) throws RoomDoesNotExistException, RemoteException;

    List<String> get_players_card(String playerId) throws RemoteException;

    void give_turn(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    void start_turn(String playerId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    boolean is_turn(String playername, String currentRoomName) throws RemoteException, RoomDoesNotExistException;
    String whose_turn(String currentRoomName)throws RemoteException, RoomDoesNotExistException;

    String  draw_card(String currentRoomName, String player) throws RemoteException, RoomDoesNotExistException;
    void  discard_card(String currentRoomName, String playerId, String card)throws RemoteException, RoomDoesNotExistException;

    String showTopCard(String currentRoomName) throws RemoteException, RoomDoesNotExistException;
    String showTopCardAblage(String currentRoomName)throws RemoteException, RoomDoesNotExistException;

    int getNumberOfPlayers(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    int getRequiredNumberOfPlayers(String currentRoomName) throws RemoteException, RoomDoesNotExistException;


    String getWinner(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    boolean winnerExists(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    void setWinner(String playername, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    int howManyEggs(String playerId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;
    void incEggNumber(String playerId, String currentRoomName, int number)throws RemoteException, RoomDoesNotExistException;

    boolean lay_eggs(String playerId, String currentRoomName, ArrayList<String> selected) throws RemoteException, RoomDoesNotExistException;

    void give_rooster_card(String playerId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    String get_rooster_holder(String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    boolean want_rooster_card(String playerId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    void steal_card(String stealerId, String victimId, Card cardToSteal, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    List<String> getAvailablePlayersToSteal(String chooserId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    List<Card> getAvailableCardsToSteal(String stealerId, String victimId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;
}
