package name.emu.decimatio;

import java.util.Collections;
import java.util.List;

public class GameLogic {

    public static synchronized void addPlayer(GameState gameState, Player player) {
        if (!gameState.getPlayers().contains(player)) {
            gameState.getPlayers().add(player);
        }
    }

    public static void launchGame(GameState gameState) {
        List<Legionnaire> legionnaireList = gameState.getLegionnaires();

        // create player characters
        for (Player player: gameState.getPlayers()) {
            Legionnaire legionnaire = Legionnaire.builder().playerCharacter(true).name(player.getName()).upcomingMove(Move.NONE).build();
            player.setCharacter(legionnaire);
            legionnaireList.add(legionnaire);
        }

        // create AI characters
        while (legionnaireList.size()<10) {
            Legionnaire legionaire = Legionnaire.builder().playerCharacter(false).name("L"+legionnaireList.size()).upcomingMove(Move.NONE).build();
            legionnaireList.add(legionaire);
        }

        Collections.shuffle(legionnaireList);
        gameState.setStatus(GameStatus.RUNNING);
    }

    public static void performTurn(GameState gameState) {

    }
}
