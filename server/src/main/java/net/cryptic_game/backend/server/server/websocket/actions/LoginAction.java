package net.cryptic_game.backend.server.server.websocket.actions;

import com.google.gson.JsonObject;
import net.cryptic_game.backend.base.data.user.User;
import net.cryptic_game.backend.server.server.ServerResponseType;
import net.cryptic_game.backend.server.server.websocket.WebSocketAction;

import java.util.UUID;

import static net.cryptic_game.backend.base.utils.JsonUtils.getString;
import static net.cryptic_game.backend.server.server.websocket.WebSocketUtils.build;

public class LoginAction extends WebSocketAction {

    public LoginAction() {
        super("login");
    }

    @Override
    public JsonObject handleRequest(User user, UUID tag, JsonObject data) throws Exception {
        if (user != null)
            return build(ServerResponseType.FORBIDDEN, "ALREADY_LOGGED_IN", tag);

        String name = getString(data, "name");
        String password = getString(data, "password");

        if (name == null)
            return build(ServerResponseType.BAD_REQUEST, "MISSING_NAME", tag);

        if (password == null)
            return build(ServerResponseType.BAD_REQUEST, "MISSING_PASSWORD", tag);

        return null;
    }
}