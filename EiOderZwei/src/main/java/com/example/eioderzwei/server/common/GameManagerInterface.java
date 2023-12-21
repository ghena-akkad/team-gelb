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
    int getPlayersIndex(String roomname, String name)throws RemoteException, RoomDoesNotExistException;

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

    void steal_card(String cardToSteal, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    List<String> getAvailablePlayersToSteal(String chooserId, String currentRoomName) throws RemoteException, RoomDoesNotExistException;

    String get_victim(String currentRoomName) throws RemoteException, RoomDoesNotExistException;
    String get_stealer(String currentRoomName)throws RemoteException, RoomDoesNotExistException;
    void steal_all_cards_except_one(String roomname, String cardNotToSteal)throws RemoteException, RoomDoesNotExistException;

    void set_victim(String currentRoomName, String victim)throws RemoteException, RoomDoesNotExistException;
    void set_stealer(String currentRoomName, String stealer) throws RemoteException, RoomDoesNotExistException;
    boolean get_fox_prompt(String currentRoomName)throws RemoteException, RoomDoesNotExistException;
    void set_fox_prompt(String currentRoomName, boolean b)throws RemoteException, RoomDoesNotExistException;
    boolean get_prompt_required(String currentRoomName)throws RemoteException, RoomDoesNotExistException;
    void set_prompt_required(String currentRoomName, boolean b)throws RemoteException, RoomDoesNotExistException;
    boolean get_fox_karte_gezogen(String currentRoomName)throws RemoteException, RoomDoesNotExistException;
    void set_fox_karte_gezogen(String currentRoomName, boolean b)throws RemoteException, RoomDoesNotExistException;
    boolean getGezogen(String roomname)throws RemoteException, RoomDoesNotExistException;
    void setGezogen(String roomname, boolean gezogen)throws RoomDoesNotExistException, RemoteException;
}
