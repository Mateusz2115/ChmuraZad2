import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    private static final int SERVER_PORT = 8080;
    private static final String AUTHOR_NAME = "Matesz Zajaczkoski";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        server.createContext("/", new Handler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + SERVER_PORT );
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String ip = Inet4Address.getLocalHost().getHostAddress();

            // Zapis informacji do logów
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            String logMessage = String.format("Server started by %s at %s. Listening on port %d.",
                     AUTHOR_NAME, currentDate, SERVER_PORT);
            System.out.println(logMessage);

            // Odpowiedź HTML
            String htmlResponse = String.format(
                    "<html>\n" +
                            "<body>\n" +
                            "<h1>Informacje o autorze</h1>\n" +
                            "<p>Adres IP: %s</p>\n" +
                            "<p>Data i czas: %s</p>\n" +
                            "</body>\n" +
                            "</html>",
                    ip,
                    dateFormat.format(currentDate)
            );

            exchange.sendResponseHeaders(200, htmlResponse.getBytes().length);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(htmlResponse.getBytes());
            outputStream.close();
        }
    }
}