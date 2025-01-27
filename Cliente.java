import java.io.*;
import java.net.*;

/**
 * @author Jose Manuel Flores Marzo
            * @version 1.0
            */
    public class Cliente {

        /**
         * Método principal que ejecuta el cliente.
         *
         * Este método crea una conexión con el servidor, envía un conjunto de
         * mensajes y recibe las respuestas hasta que se termine la interacción
         * o se reciba un error.
         *
         * @param args Los argumentos de línea de comandos (no utilizados).
         */
        public static void main(String[] args) {
            final String HOST = "127.0.0.1"; // Dirección del servidor
            final int PUERTO = 8000;          // Puerto del servidor

            try (Socket socket = new Socket(HOST, PUERTO)) {
                System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);

                // Streams para comunicación
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

                // Flujo de mensajes del cliente
                String[] mensajes = {
                        "¿Quién es?",
                        "¿Qué vienes a buscar?",
                        "Ya es tarde",
                        "Porque ahora soy yo la que quiere estar sin ti"
                };

                // Enviar y recibir mensajes
                for (String mensaje : mensajes) {
                    System.out.println("Cliente: " + mensaje);
                    salida.println(mensaje); // Enviar mensaje al servidor

                    String respuesta = entrada.readLine(); // Leer respuesta del servidor
                    System.out.println("Servidor: " + respuesta);

                    // Si el servidor responde con "Error", detener el flujo
                    if ("Error".equalsIgnoreCase(respuesta)) {
                        System.out.println("Se recibió un error. Cerrando conexión...");
                        break;
                    }
                }

                System.out.println("Diálogo terminado. Cerrando conexión...");

            } catch (IOException e) {
                System.out.println("Error en la conexión con el servidor: " + e.getMessage());
            }
        }
    }