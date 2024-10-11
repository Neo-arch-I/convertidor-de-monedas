import java.util.Scanner;

public class AplicacionPrincipal {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        String menuOpciones = """
             *******************************************
             Bienvenido/a al Convertidor de Monedas =]
             
             1) Dólar =>> Peso argentino
             2) Peso argentino =>> Dólar
             3) Dólar =>> Real brasileño
             4) Real brasileño =>> Dólar
             5) Dólar =>> Peso colombiano
             6) Peso colombiano =>> Dólar
             7) Salir
             Elija una opción válida:
             *******************************************
             """;

        enum Estado {
            CONTINUAR, DETENER
        }

        Estado condicion = Estado.CONTINUAR;

        while (condicion == Estado.CONTINUAR) {
            System.out.println(menuOpciones);

            Integer opcionSeleccionada = Integer.parseInt(entrada.nextLine());

            // Si la opción es válida, procede con la conversión
            if (opcionSeleccionada >= 1 && opcionSeleccionada < 7) {
                System.out.println("Ingrese el valor a convertir: ");

                String valorIngresado = entrada.nextLine();

                if (valorIngresado.matches(".*[.,].*[.,].*")) {
                    // Si tiene múltiples comas o puntos, retorna al inicio
                    System.out.println("Formato de número no válido");
                    continue;
                } else if (valorIngresado.contains(",") && valorIngresado.contains(".")) {
                    // Si tiene coma y punto, el punto marca el decimal
                    valorIngresado = valorIngresado.replace(",", "");
                } else if (valorIngresado.contains(",")) {
                    valorIngresado = valorIngresado.replace(",", ".");
                }

                Double valorConvertir = Double.valueOf(valorIngresado);

                MiConversorDeMonedas conversor = new MiConversorDeMonedas(opcionSeleccionada, valorConvertir);

                // Ejecuta la conversión
                conversor.realizarConversion();
                // Muestra el resultado final
                System.out.println(conversor.obtenerMensajeResultado());
            } else if (opcionSeleccionada == 7) {
                System.out.println("Cerrando la aplicación");
                condicion = Estado.DETENER;
            } else {
                // Si la opción está fuera del rango, muestra este mensaje
                System.out.println("Ingrese un valor entre 1 y 7");
            }

        }

    }
}
