package com.example.eioderzwei.server;
import java.rmi.RemoteException;
import java.util.*;
import com.example.eioderzwei.client.UserInfo;
import com.example.eioderzwei.server.common.GameManagerInterface;
import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;

import static com.example.eioderzwei.server.CardType.ROOSTER;

/* TODO Ranklist: Beide methoden nehmen room number als Parameter und finden den Raum mithilfe von roomsManager
    1) methode für update von rankList
    2) methode get_rankList, die rankList zurückgibt

   */
/* TODO neue Runde: Methoden start_round und end_round schreiben



/**
 *
 * Spiellogik
 */
public class GameManager implements GameManagerInterface {
    private RoomsManagerHelper roomsManager;
    private LoginManagerHelper loginManager;
    private static final Map<String, CardType> imageCardMap = new HashMap<>();

    private Map<CardType, String> cardImageMap;
    public GameManager() {
        this.roomsManager = new RoomsManagerHelper();
        this.loginManager = new LoginManagerHelper();
        get_map();
    }
    public void  get_map(){
        imageCardMap.put("com/example/eioderzwei/image/2Korn.png", CardType.TWONORMAL);
        imageCardMap.put("com/example/eioderzwei/image/3Korn.png", CardType.THREENORMAL);
        imageCardMap.put("com/example/eioderzwei/image/4Korn.png", CardType.FOURNORMAL);
        imageCardMap.put("com/example/eioderzwei/image/1BioKorn.png", CardType.ONEBIO);
        imageCardMap.put("com/example/eioderzwei/image/2BioKörner.png", CardType.TWOBIO);
        imageCardMap.put("com/example/eioderzwei/image/3BioKorn.png", CardType.THREEBIO);
        imageCardMap.put("com/example/eioderzwei/image/Fucks.png",CardType.FOX);
        imageCardMap.put("com/example/eioderzwei/image/Kuckuck.png", CardType.CUCKOO);
        imageCardMap.put("com/example/eioderzwei/image/Hahnkarte.png", ROOSTER);
    }

    public CardType getCard(String s) {
        return imageCardMap.get(s);
    }
    public void initialize_rooster(String currentRoomName) throws RoomDoesNotExistException{
        Random random = new Random();
        int number = roomsManager.getGameroom(currentRoomName).getPlayersNumber();
        String random_player = roomsManager.getGameroom(currentRoomName).getPlayerIds().get(random.nextInt(number));
        give_rooster_card(random_player, currentRoomName);
    }
    public boolean has_game_started(String currentRoomName) throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName).hasGameStarted();
    }
    public void start(String currentRoomName) throws RoomDoesNotExistException {
         roomsManager.getGameroom(currentRoomName).startGame();
    }
    public List<String> get_players_card(String player_id) {
        List<String> cards = new ArrayList<>();
        Player player = loginManager.getPlayer(player_id);
        for (Card card : player.getHand()) {
            String imagePath = card.getImagePath();
            cards.add(imagePath);
        }
        return cards;
    }
    public void give_turn(String currentRoomName) throws RoomDoesNotExistException {
        roomsManager.getGameroom(currentRoomName).nextPlayerTurn();
    }
    public void start_turn(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        roomsManager.getGameroom(currentRoomName).setCurrentPlayer(playerId);
    }
    public boolean is_turn(String playername, String currentRoomName) throws RoomDoesNotExistException {
        Player player = loginManager.getPlayer(playername);
        return roomsManager.getGameroom(currentRoomName).getCurrentPlayer().equals(player);
    }
    public String whose_turn(String currentRoomName)throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName).getCurrentPlayer().getUsername();
    }
    public void  draw_card(String currentRoomName, String player) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDrawPile().drawCard();
        loginManager.getPlayer(player).addCardToHand(card);
        String imagePath = card.getImagePath();


    }
    public String showTopCard(String currentRoomName) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDrawPile().showTopCard();
        String imagePath = card.getImagePath();
        return imagePath;
    }
    public String showTopCardAblage(String currentRoomName) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDiscardPile().viewTopCard();
        if(card!=null){
            String imagePath = card.getImagePath();
            return imagePath;
        }
        String leer = "";
        return leer;
    }
    public int getNumberOfPlayers(String currentRoomName) throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName).getPlayerIds().size();
    }
    public int getRequiredNumberOfPlayers(String currentRoomName)throws RoomDoesNotExistException {
            GameRoom g = roomsManager.getGameroom(currentRoomName);
            if(g != null) {

                return g.getRequiredNumberOfPlayers();
            }
            else{
                return -1;
            }
    }
    public String getWinner(String currentRoomName) throws RoomDoesNotExistException {
        Player player = roomsManager.getGameroom(currentRoomName).getWinner();
        return player.getUsername();
    }
    public boolean winnerExists(String currentRoomName) throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName).getWinner() != null;
    }
    public void setWinner(String playername, String currentRoomName) throws RoomDoesNotExistException {
        Player player = loginManager.getPlayer(playername);
        roomsManager.getGameroom(currentRoomName).setWinner(player);
    }
    public int howManyEggs(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player player = room.getPlayerMap().get(playerId);
        return player.getEggCount();
    }
    public boolean lay_eggs(String playerId, String currentRoomName, ArrayList<String> selected) throws RoomDoesNotExistException {

        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player player = room.getPlayerMap().get(playerId);



        ArrayList<Card> biocards = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();

        int grainCount = 0;
        int bioGrainCount = 0;

        for(String s: selected){
            CardType t = getCard(s);

            Card k = player.get_card(t);
            if(k.getBio()){
                biocards.add(k);

            }
            else{
                cards.add(k);
            }

        }

        for (Card card : biocards) {

            bioGrainCount += card.getWert();
        }


        int eggsLaidBio = (bioGrainCount / 5)*2;
        int rest = bioGrainCount%5;

        for (Card card : cards) {

            grainCount += card.getWert();
        }
        grainCount+=rest;
        int eggslaidnotbio= (grainCount / 5);
        int eggsLaid = eggsLaidBio+eggslaidnotbio;
        if (eggsLaid > 0) {
            for (Card card : biocards) {
                player.removeCard(card);
                roomsManager.getGameroom(currentRoomName).getDiscardPile().discard_card(card);

            }
            for (Card card : cards) {
                player.removeCard(card);
                roomsManager.getGameroom(currentRoomName).getDiscardPile().discard_card(card);

            }

            player.incrementEggCountBy(eggsLaid);

            System.out.println("eggsLaid = " + eggsLaid);


            return true;
        }

        return false;
    }
    public void give_rooster_card(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        roomsManager.getGameroom(currentRoomName).setRoosterCardHolder(playerId);
        Card card = new Card(ROOSTER);
        loginManager.getPlayer(playerId).addCardToHand(card);
    }
    public String get_rooster_holder(String currentRoomName) throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName).getRoosterCardHolder();
    }
    public boolean want_rooster_card(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player playerRequesting = room.getPlayerMap().get(playerId);
        String roosterHolderId = room.getRoosterCardHolder();
        Player roosterHolder = roosterHolderId != null ? room.getPlayerMap().get(roosterHolderId) : null;
        if (roosterHolder == null || playerRequesting.getEggCount() < roosterHolder.getEggCount()) {
            room.setRoosterCardHolder(playerId);
            return true;
        }
        return false;
    }
    public void steal_card(String stealerId, String victimId, Card cardToSteal, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player stealer = room.getPlayerMap().get(stealerId);
        Player victim = room.getPlayerMap().get(victimId);
        // Steal the specified card from the victim
        if (victim.getHand().contains(cardToSteal)) {
            victim.removeCard(cardToSteal); // Remove the card from the victim's hand
            stealer.addCardToHand(cardToSteal); // Add the card to the stealer's hand
        }
        // Optionally, handle the case where the card is no longer in the victim's hand
    }
    public List<String> getAvailablePlayersToSteal(String chooserId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        List<String> availablePlayerIds = new ArrayList<>(room.getPlayerIds());
        // Remove the chooser from the list
        availablePlayerIds.remove(chooserId);
        return availablePlayerIds; // Return the list of available players
    }
    public List<Card> getAvailableCardsToSteal(String stealerId, String victimId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player victim = room.getPlayerMap().get(victimId);
        return new ArrayList<>(victim.getHand()); // Return a list of cards from the victim's hand
    }
}