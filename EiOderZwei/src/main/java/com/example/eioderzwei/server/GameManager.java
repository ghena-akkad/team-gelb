package com.example.eioderzwei.server;

import java.rmi.RemoteException;
import java.util.*;

import com.example.eioderzwei.client.UserInfo;
import com.example.eioderzwei.server.common.GameManagerInterface;
import com.example.eioderzwei.server.exceptions.PlayerNameAlreadyExistsException;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;
/**
 *
 * Spiellogik
 */
public class GameManager implements GameManagerInterface {

    private RoomsManagerHelper roomsManager;
    private LoginManagerHelper loginManager;

    private Map<CardType, String> cardImageMap;
    public GameManager() {
        this.roomsManager = new RoomsManagerHelper();
        this.loginManager = new LoginManagerHelper();
        get_map();
    }

    public void  get_map(){
        Map<CardType, String> cardImageMap = new HashMap<>();
        cardImageMap.put(CardType.TWONORMAL, "com/example/eioderzwei/image/2Korn.png");
        cardImageMap.put(CardType.THREENORMAL, "com/example/eioderzwei/image/3Korn.png");
        cardImageMap.put(CardType.FOURNORMAL, "com/example/eioderzwei/image/4Korn.png");
        cardImageMap.put(CardType.ONEBIO, "com/example/eioderzwei/image/1BioKorn.png");
        cardImageMap.put(CardType.TWOBIO, "com/example/eioderzwei/image/2BioKörner.png");
        cardImageMap.put(CardType.THREEBIO, "com/example/eioderzwei/image/3BioKorn.png");
        cardImageMap.put(CardType.FOX, "com/example/eioderzwei/image/Fucks.png");
        cardImageMap.put(CardType.CUCKOO, "com/example/eioderzwei/image/Kuckuck.png");
        cardImageMap.put(CardType.ROOSTER, "com/example/eioderzwei/image/Hahnkarte.png");
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
    public String  draw_card(String currentRoomName) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDrawPile().drawCard();
        String imagePath = card.getImagePath();

        return imagePath;

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

            System.out.println("getrequired ok");


            GameRoom g = roomsManager.getGameroom(currentRoomName);
            if(g != null) {
                System.out.println("g is not null");

                return g.getRequiredNumberOfPlayers();
            }
            else{
                System.out.println("g is null");
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
    public boolean lay_eggs(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player player = room.getPlayerMap().get(playerId);

        int grainCount = 0;
        int bioGrainCount = 0;
        List<Card> cardsToRemove = new ArrayList<>();

        // Count the grains and bio grains
        for (Card card : player.getHand()) {
            if (card.isGrainCard()) {
                cardsToRemove.add(card);
                if (card.getBio()) { // Assuming getBio method in Card class checks for bio grain
                    bioGrainCount += 2; // Bio grains count double
                } else {
                    grainCount += 1;
                }
            }
        }

        int totalGrainCount = grainCount + bioGrainCount;
        int eggsLaid = totalGrainCount / 5; // 5 grains needed per egg

        if (eggsLaid > 0) {
            // Remove used grain cards from the player's hand
            for (Card card : cardsToRemove) {
                player.getHand().remove(card);
            }

            // Increment the player's egg count
            player.incrementEggCountBy(eggsLaid);

            return true; // Egg laying was successful
        }

        return false; // Not enough grains to lay any eggs
    }

    public void give_rooster_card(String playerId, String currentRoomName) throws RoomDoesNotExistException {
        roomsManager.getGameroom(currentRoomName).setRoosterCardHolder(playerId);
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

    public void steal_card(String stealerId, String victimId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player stealer = room.getPlayerMap().get(stealerId);
        Player victim = room.getPlayerMap().get(victimId);

        // Example logic to steal a random grain card from the victim
        Card stolenCard = victim.getRandomGrainCard(); // Assuming this method exists in Player class
        if (stolenCard != null) {
            victim.removeCard(stolenCard); // Remove the card from the victim's hand
            stealer.addCardToHand(stolenCard);   // Add the card to the stealer's hand
        }
    }

    public String choosePlayerToSteal(String chooserId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        List<String> playerIds = room.getPlayerIds();

        playerIds.remove(chooserId);
        if (!playerIds.isEmpty()) {
            Collections.shuffle(playerIds);
            return playerIds.get(0);
        }
        return null;
    }

    public String choose_cards_to_steal(String stealerId, String victimId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player victim = room.getPlayerMap().get(victimId);

        Card chosenCard = victim.chooseCardToSteal();
        String imagePath = chosenCard.getImagePath();
        return imagePath;
    }
}

