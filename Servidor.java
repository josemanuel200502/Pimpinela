import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        final int PUERTO = 8000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado. Esperando conexión en el puerto " + PUERTO + "...");

            try (Socket cliente = servidor.accept()) {
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());

                // Streams para comunicación
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

                // Variable para controlar el flujo del diálogo
                int estado = 0;
                String mensaje;

                while ((mensaje = entrada.readLine()) != null) {
                    switch (estado) {
                        case 0: // Espera "¿Quién es?"
                            if ("¿Quién es?".equalsIgnoreCase(mensaje)) {
                                salida.println("Soy yo");
                                estado = 1;
                            } else {
                                salida.println("Error");
                            }
                            break;

                        case 1: // Espera "¿Qué vienes a buscar?"
                            if ("¿Qué vienes a buscar?".equalsIgnoreCase(mensaje)) {
                                salida.println("A ti");
                                estado = 2;
                            } else {
                                salida.println("Error");
                            }
                            break;

                        case 2: // Espera "Ya es tarde"
                            if ("Ya es tarde".equalsIgnoreCase(mensaje)) {
                                salida.println("¿Por qué?");
                                estado = 3;
                            } else {
                                salida.println("Error");
                            }
                            break;

                        case 3: // Espera "Porque ahora soy yo la que quiere estar sin ti"
                            if ("Porque ahora soy yo la que quiere estar sin ti".equalsIgnoreCase(mensaje)) {
                                salida.println("Por eso vete, olvida mi nombre, mi cara, mi casa y pega la vuelta");
                                System.out.println("Diálogo completado. Cerrando conexión...");
                                return; // Termina el servidor después del mensaje final
                            } else {
                                salida.println("Error");
                            }
                            break;

                        default:
                            salida.println("Error inesperado");
                            break;
                    }
                }

            } catch (IOException e) {
                System.out.println("Error en la comunicación con el cliente: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
