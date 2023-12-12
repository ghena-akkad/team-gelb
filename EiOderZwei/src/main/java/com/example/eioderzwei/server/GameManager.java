package com.example.eioderzwei.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.example.eioderzwei.server.exceptions.RoomDoesNotExistException;
/**
 * Spiellogik
 */
public class GameManager {

    private RoomsManager roomsManager;
    private String currentRoomName;
    private LoginManager loginManager;

    private Map<CardType, String> cardImageMap;
    public GameManager(RoomsManager roomsManager, String currentRoomName) {
        this.roomsManager = roomsManager;
        this.currentRoomName = currentRoomName;
    }

    public void  get_map(){
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
    public GameRoom getCurrentRoom() throws RoomDoesNotExistException {
        return roomsManager.getGameroom(currentRoomName);
    }
    public boolean has_game_started() throws RoomDoesNotExistException {
        return getCurrentRoom().hasGameStarted();
    }
    public List<Card> get_players_card(Player player) {
        return player.getHand();
    }
    public void give_turn() throws RoomDoesNotExistException {
       getCurrentRoom().nextPlayerTurn();
    }
    public void start_turn(String playerId) throws RoomDoesNotExistException {
        getCurrentRoom().setCurrentPlayer(playerId);
    }
    public boolean is_turn(Player player) throws RoomDoesNotExistException {
        return getCurrentRoom().getCurrentPlayer().equals(player);
    }
    public Card draw_card() throws RoomDoesNotExistException {
        return getCurrentRoom().getDrawPile().drawCard();
    }
    public int getNumberOfPlayers() throws RoomDoesNotExistException {
        return getCurrentRoom().getPlayerIds().size();
    }
    public Player getWinner() throws RoomDoesNotExistException {
        return getCurrentRoom().getWinner();
    }
    public boolean winnerExists() throws RoomDoesNotExistException {
        return getCurrentRoom().getWinner() != null;
    }
    public void setWinner(Player player) throws RoomDoesNotExistException {
        getCurrentRoom().setWinner(player);
    }
    public int howManyEggs(Player player) {
        return player.getEggCount();
    }
    public boolean lay_eggs(String playerId) throws RoomDoesNotExistException {
        GameRoom room = getCurrentRoom();
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

    public void give_rooster_card(String playerId) throws RoomDoesNotExistException {
        getCurrentRoom().setRoosterCardHolder(playerId);
    }

    public boolean want_rooster_card(String playerId) throws RoomDoesNotExistException {
        GameRoom room = getCurrentRoom();
        Player playerRequesting = room.getPlayerMap().get(playerId);
        String roosterHolderId = room.getRoosterCardHolder();

        Player roosterHolder = roosterHolderId != null ? room.getPlayerMap().get(roosterHolderId) : null;

        if (roosterHolder == null || playerRequesting.getEggCount() < roosterHolder.getEggCount()) {
            room.setRoosterCardHolder(playerId);
            return true;
        }
        return false;
    }

    public void steal_card(String stealerId, String victimId) throws RoomDoesNotExistException {
        GameRoom room = getCurrentRoom();
        Player stealer = room.getPlayerMap().get(stealerId);
        Player victim = room.getPlayerMap().get(victimId);

        // Example logic to steal a random grain card from the victim
        Card stolenCard = victim.getRandomGrainCard(); // Assuming this method exists in Player class
        if (stolenCard != null) {
            victim.removeCard(stolenCard); // Remove the card from the victim's hand
            stealer.addCardToHand(stolenCard);   // Add the card to the stealer's hand
        }
    }

    public String choosePlayerToSteal(String chooserId) throws RoomDoesNotExistException {
        GameRoom room = getCurrentRoom();
        List<String> playerIds = room.getPlayerIds();

        // Example logic: choose a random player other than the chooser to steal from
        playerIds.remove(chooserId); // Remove the chooser from the list
        if (!playerIds.isEmpty()) {
            Collections.shuffle(playerIds); // Shuffle the list
            return playerIds.get(0);       // Return the first player in the shuffled list
        }
        return null; // Return null if no other players are available to steal from
    }

    public Card choose_cards_to_steal(String stealerId, String victimId) throws RoomDoesNotExistException {
        GameRoom room = getCurrentRoom();
        Player victim = room.getPlayerMap().get(victimId);

        // Example logic: choose a specific card from the victim's hand
        // This could be a random choice or based on some criteria
        Card chosenCard = victim.chooseCardToSteal(); // Assuming this method exists in Player class
        return chosenCard;
    }
}

