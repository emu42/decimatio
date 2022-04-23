package name.emu.decimatio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameSessionSingleton {

    private GameState gameState = createNew();

    private static GameSessionSingleton THE_SINGLETON = new GameSessionSingleton();

    private static Map<String, Player> sessionToPlayerMap = new HashMap<>();

    private static GameState createNew() {
        return GameState.builder()
            .status(GameStatus.LOBBY)
            .tenthSlotPos(5)
            .legionnaires(new ArrayList<>())
            .players(new ArrayList<>())
            .build();
    }

    public GameState getGameState() {
        return gameState;
    }

    public static GameSessionSingleton getTheSingleton() {
        return THE_SINGLETON;
    }

    public synchronized static Player findOrCreateForSessionId(String sessionId) {
        if (!sessionToPlayerMap.containsKey(sessionId)) {
            Player player = Player.builder().name(sessionId).build();
            sessionToPlayerMap.put(sessionId, player);
        }
        return sessionToPlayerMap.get(sessionId);
    }
}
