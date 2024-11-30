package ifrs.edu.com.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import io.github.cdimascio.dotenv.Dotenv;

public class WebSocketConfig {
    private static Dotenv dotenv = Dotenv.load();

    private HttpClient client;
    private WebSocket.Listener listener;
    private WebSocket webSocket;

    public WebSocketConfig() {
        this.client = HttpClient.newHttpClient();

        this.listener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                Listener.super.onOpen(webSocket);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                System.out.println("Received message: " + data);

                webSocket.sendText("Hello from client", true);
                return CompletableFuture.completedFuture(null);

            }

            @Override
            public void onError(WebSocket webSocket, Throwable error) {
                System.out.println("Error: " + error.getMessage());
            }
        };

        this.webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create(dotenv.get("WS_URI")), listener)
                .join();

        webSocket.sendText("Hello server", true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException err) {
            err.printStackTrace();
        }
    }
}
