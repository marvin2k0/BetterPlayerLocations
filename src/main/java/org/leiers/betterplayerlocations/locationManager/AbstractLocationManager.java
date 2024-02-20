package org.leiers.betterplayerlocations.locationManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractLocationManager implements LocationManager {
    protected HttpResponse<String> performRequest(String url) throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newBuilder().build();
        final HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
