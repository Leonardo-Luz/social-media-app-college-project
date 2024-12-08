package ifrs.edu.com.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;

public class WebSocketConfig {
    private static Dotenv dotenv = Dotenv.load();

    private static HttpClient client;
    private static WebSocket.Listener listener;
    public static CompletableFuture<WebSocket> build;
    public static WebSocket ws;

    public static boolean isConnected = false;

    public static void start(ChatConfig controller) {

        WebSocketConfig.client = HttpClient.newHttpClient();

        WebSocketConfig.listener = new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket ws) {
                System.out.println("WebSocket connection established.");
                isConnected = true;
                Listener.super.onOpen(ws);
            }

            @Override
            public CompletionStage<?> onClose(WebSocket ws, int statusCode, String reason) {
                System.out.println("WebSocket closed with status: " + statusCode + " and reason: " + reason);

                isConnected = false;

                Listener.super.onClose(ws, statusCode, reason);
                return CompletableFuture.completedFuture(null);
            }

            @Override
            public CompletionStage<?> onText(WebSocket ws, CharSequence data, boolean last) {
                System.out.println("Received message: " + data);

                System.out.println("Output closed: " + ws.isOutputClosed());
                System.out.println("Input closed: " + ws.isInputClosed());

                System.out.println("Is last part of message? " + last);

                if (controller != null)
                    CompletableFuture.runAsync(() -> {
                        Platform.runLater(() -> {
                            if (data.toString().endsWith(":"))
                                controller.loadTable();
                            // else if(data.toString().endsWith("!"))
                            // onlineUsers.add(data.toString().replaceFirst(".$", ""));
                        });
                    });

                Listener.super.onText(ws, data, last);
                return CompletableFuture.completedFuture(null);
            }

            @Override
            public void onError(WebSocket ws, Throwable error) {
                System.out.println("Error: " + error.getMessage());

                Listener.super.onError(ws, error);
            }
        };

        build = client.newWebSocketBuilder()
                .connectTimeout(Duration.ofMillis(5000))
                .buildAsync(URI.create(dotenv.get("WS_URI")), listener);

        ws = build.join();
    }

    public static void sendMessage(String message) {
        if (isConnected) {
            ws.sendText(message, true);
            System.out.println("Sent message: " + message);
        } else {
            System.out.println("WebSocket is not connected. Cannot send message.");
        }
    }

    public static void close() {
        if (isConnected) {
            ws.sendText("Goodbye", true);

            // if (ws.sendClose(WebSocket.NORMAL_CLOSURE, "Closing
            // connection").isCancelled()) {
            // ws.abort();
            // System.out.println("WebSocket connection aborted.");
            // } else
            // System.out.println("WebSocket connection closed.");

            ws.abort();
            System.out.println("WebSocket connection aborted.");
            isConnected = false;
        }
    }
}
