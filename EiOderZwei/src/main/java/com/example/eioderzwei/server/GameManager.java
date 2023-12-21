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
        //String random_player = roomsManager.getGameroom(currentRoomName).getPlayerIds().get(random.nextInt(number));
        String random_player = roomsManager.getGameroom(currentRoomName).getPlayerIds().get(0);
        Card card = new Card(ROOSTER);
        roomsManager.getGameroom(currentRoomName).setRoosterCardHolder(random_player);

        loginManager.getPlayer(random_player).addCardToHand(card);

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
    public int getPlayersIndex(String roomname, String name) throws RoomDoesNotExistException {
        GameRoom room =roomsManager.getGameroom(name);
        return room.getPlayersIndex(name);



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
    public String  draw_card(String currentRoomName, String player) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDrawPile().drawCard();
        loginManager.getPlayer(player).addCardToHand(card);
        String imagePath = card.getImagePath();
        return imagePath;


    }
    public void  discard_card(String currentRoomName, String playerId, String card) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player player = room.getPlayerMap().get(playerId);
        CardType t = getCard(card);
        Card k = player.get_card(t);
        player.removeCard(k);






    }



    public String showTopCard(String currentRoomName) throws RoomDoesNotExistException {
        Card card = roomsManager.getGameroom(currentRoomName).getDrawPile().showTopCard();
        if(card!=null){
            String imagePath = card.getImagePath();
            return imagePath;
        }
        String leer = "";
        return leer;
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
    public void incEggNumber(String playerId, String currentRoomName, int number) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player player = room.getPlayerMap().get(playerId);
         player.incrementEggCountBy(number);
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
            k.setUsed(true);
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



            return true;
        }

        return false;
    }
    public void give_rooster_card(String playerId, String currentRoomName) throws RoomDoesNotExistException {

        String holder = roomsManager.getGameroom(currentRoomName).getRoosterCardHolder();

        Player holderplayer =loginManager.getPlayer(holder);
        Card card = holderplayer.get_card(ROOSTER);
        holderplayer.removeCard(card);
        roomsManager.getGameroom(currentRoomName).setRoosterCardHolder(playerId);
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

            return true;
        }

        return false;
    }
    public void steal_card(String cardToSteal, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        Player stealer = room.getPlayerMap().get(room.get_stealer());
        Player victim = room.getPlayerMap().get(room.get_victim());

        ArrayList<Card> victimscards = victim.getHand();
        Iterator<Card> iterator = victimscards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            String imagePath = card.getImagePath();
            if (imagePath.equals(cardToSteal)) {
                    iterator.remove();
                    stealer.addCardToHand(card);

                    break;
                }
            }
        set_fox_karte_gezogen(currentRoomName, false);
        if(!room.get_stealer().equals(room.getRoosterCardHolder()) || room.getGezogen()){
            setGezogen(currentRoomName, false);
            give_turn(currentRoomName);



        }
        CardType t = getCard("com/example/eioderzwei/image/Fucks.png");
        Card k = stealer.get_card(t);
        stealer.removeCard(k);
        room.getDiscardPile().discard_card(k);




    }
    public void steal_all_cards_except_one(String roomname, String cardNotToSteal)throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(roomname);

        String s = room.get_stealer();
        String v = room.get_victim();



        Player stealer = room.getPlayerMap().get(s);
        Player victim = room.getPlayerMap().get(v);
        boolean saved = false;
        ArrayList<Card> victimscards = victim.getHand();

        Iterator<Card> iterator = victimscards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            String imagePath = card.getImagePath();
            if (imagePath.equals(cardNotToSteal)) {
                if (!saved) {
                    saved = true;
                } else {
                    iterator.remove();
                    stealer.addCardToHand(card);
                }
            } else if (card.isGrainCard()) {
                iterator.remove();
                stealer.addCardToHand(card);
            }

        }
        set_fox_karte_gezogen(roomname, false);
        if(!room.get_stealer().equals(room.getRoosterCardHolder()) || room.getGezogen()){

            setGezogen(roomname, false);
            give_turn(roomname);


        }
        CardType t = getCard("com/example/eioderzwei/image/Fucks.png");
        Card k = stealer.get_card(t);
        stealer.removeCard(k);
        room.getDiscardPile().discard_card(k);

        room.set_stealer("");
        room.set_victim("");
        room.set_foxPrompt(false);
        room.setPromptRequired(false);



    }
    public List<String> getAvailablePlayersToSteal(String chooserId, String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        List<String> availablePlayerIds = new ArrayList<>(room.getPlayerIds());
        availablePlayerIds.remove(chooserId);
        return availablePlayerIds;
    }
    public String get_victim(String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        return room.get_victim();
    }

    public void set_victim(String currentRoomName, String victim) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
         room.set_victim(victim);
    }
    public String get_stealer(String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        return room.get_stealer();
    }

    public void set_stealer(String currentRoomName, String stealer) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        room.set_stealer(stealer);
    }
    public boolean get_fox_prompt(String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        return room.hasFoxPrompt();
    }
    public void set_fox_prompt(String currentRoomName, boolean b) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        room.set_foxPrompt(b);
    }
    public boolean get_prompt_required(String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        return room.ifPromptRequired();
    }
    public void set_prompt_required(String currentRoomName, boolean b) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        room.setPromptRequired(b);
    }

    public boolean get_fox_karte_gezogen(String currentRoomName) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        return room.get_foxKarteGezogen();
    }
    public void set_fox_karte_gezogen(String currentRoomName, boolean b) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(currentRoomName);
        room.set_foxKarteGezogen(b);
    }
    public boolean getGezogen(String roomname) throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(roomname);

        return room.getGezogen();


    }
    public void setGezogen(String roomname, boolean gezogen)throws RoomDoesNotExistException {
        GameRoom room = roomsManager.getGameroom(roomname);
        room.setGezogen(gezogen);



    }

}