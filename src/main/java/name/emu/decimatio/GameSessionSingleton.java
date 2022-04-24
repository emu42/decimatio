package name.emu.decimatio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Player;

public class GameSessionSingleton {

    private static GameSessionSingleton THE_SINGLETON = new GameSessionSingleton();

    private static Map<String, Player> sessionToPlayerMap = new HashMap<>();

    private static Map<String, GameState> sessionToGameMap = new HashMap<>();

    public synchronized static GameState findForGameSessionId(String gameSessionId) {
        return sessionToGameMap.get(gameSessionId);
    }

    public synchronized static GameState createGameSession() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        GameState gameState;
        gameState =  GameState.builder()
            .gameSessionId(uuidAsString)
            .status(GameStatus.LOBBY)
            .tenthSlotPos(5)
            .commanderPos(9)
            .legionnaires(new ArrayList<>())
            .players(new ArrayList<>())
            .build();

        sessionToGameMap.put(uuidAsString, gameState);
        return gameState;
    }

    public synchronized static Player findOrCreateForSessionId(String sessionId) {
        if (!sessionToPlayerMap.containsKey(sessionId)) {
            Player player = Player.builder().name(null).build();
            sessionToPlayerMap.put(sessionId, player);
        }
        return sessionToPlayerMap.get(sessionId);
    }
}
