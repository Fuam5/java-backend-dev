package net.cryptic_game.backend.base.api.endpoint;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.cryptic_game.backend.base.api.ApiException;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public final class ApiEndpointHandler {

    @Getter
    private final ApiEndpointList apiList;
    private Set<ApiEndpointCollection> apiCollections;

    public ApiEndpointHandler() {
        this.apiCollections = new HashSet<>();
        this.apiList = new ApiEndpointList();
    }

    public void postInit() {
        try {
            this.apiList.setCollections(this.apiCollections);
            this.apiCollections = null;
        } catch (ApiException e) {
            log.error("Unable to register Api-Collections.", e);
        }
    }

    public <T extends ApiEndpointCollection> T addApiCollection(final T apiCollection) {
        if (this.apiCollections != null) {
            this.apiCollections.add(apiCollection);
            return apiCollection;
        } else {
            log.error("It's too late to register any more endpoints.");
            return null;
        }
    }
}
