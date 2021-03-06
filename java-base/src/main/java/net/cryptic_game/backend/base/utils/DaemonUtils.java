package net.cryptic_game.backend.base.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Setter;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpointCollectionData;
import net.cryptic_game.backend.base.api.endpoint.ApiEndpointData;
import net.cryptic_game.backend.base.api.endpoint.ApiParameterSpecialType;
import net.cryptic_game.backend.base.daemon.Daemon;
import net.cryptic_game.backend.base.daemon.DaemonEndpointCollectionData;
import net.cryptic_game.backend.base.daemon.DaemonEndpointData;
import net.cryptic_game.backend.base.json.JsonBuilder;
import net.cryptic_game.backend.base.json.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

public final class DaemonUtils {

    @Setter
    private static String serverUrl;

    private DaemonUtils() {
        throw new UnsupportedOperationException();
    }

    public static void notifyUser(@NotNull final UUID user, @NotNull final Enum<?> topic, @Nullable final Object data) {
        notifyUser(user, topic.name(), data);
    }

    public static void notifyUser(@NotNull final UUID user, @NotNull final String topic, @Nullable final Object data) {
        HttpClientUtils.sendAsyncRequest(serverUrl + "/daemon/notify", JsonBuilder.create("user_id", user).add("topic", topic).add("data", data).build());
    }

    @NotNull
    public static Set<ApiEndpointCollectionData> parseDaemonEndpoints(@NotNull final Daemon daemon, @NotNull final JsonArray collections) {
        return JsonUtils.fromArray(collections, new HashSet<>(), json -> {
            final JsonObject jsonObject = JsonUtils.fromJson(json, JsonObject.class);

            final String name = JsonUtils.fromJson(jsonObject.get("name"), String.class);
            final String description = JsonUtils.fromJson(jsonObject.get("description"), String.class);

            final Map<String, ApiEndpointData> endpoints = JsonUtils.fromArray(
                    JsonUtils.fromJson(jsonObject.get("endpoints"), JsonArray.class),
                    new TreeSet<>(), DaemonEndpointData.class)
                    .stream()
                    .peek(endpoint -> {
                        endpoint.setDaemon(daemon);
                        endpoint.getParameters().forEach(parameter -> {
                            if (parameter.getSpecial() == null) parameter.setSpecial(ApiParameterSpecialType.NORMAL);
                        });
                    })
                    .collect(Collectors.toMap(ApiEndpointData::getName, endpoint -> endpoint));

            final DaemonEndpointCollectionData collection = new DaemonEndpointCollectionData(name, description, null, endpoints);
            collection.setDaemon(daemon);
            return collection;
        });
    }
}
