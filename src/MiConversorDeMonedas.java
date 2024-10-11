
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MiConversorDeMonedas {
    private String monedaOrigen;
    private String monedaDestino;
    private Double montoAConvertir;
    private Integer tipoCambioSeleccionado;
    private Double resultadoConversion;

    public MiConversorDeMonedas(int tipoCambio, Double monto) {
        this.montoAConvertir = monto;
        this.tipoCambioSeleccionado = tipoCambio;
    }

    public Double obtenerMonto() {
        return montoAConvertir;
    }

    public void realizarConversion() {
        String claveApi = "7aa3866425da65f2de7ab396";
        String dolar = "USD";
        String pesoArgentino = "ARS";
        String realBrasil = "BRL";
        String pesoColombiano = "COP";

        // Determinar la conversión según la elección del usuario
        switch (tipoCambioSeleccionado) {
            case 1:
                monedaOrigen = dolar;
                monedaDestino = pesoArgentino;
                break;
            case 2:
                monedaOrigen = pesoArgentino;
                monedaDestino = dolar;
                break;
            case 3:
                monedaOrigen = dolar;
                monedaDestino = realBrasil;
                break;
            case 4:
                monedaOrigen = realBrasil;
                monedaDestino = dolar;
                break;
            case 5:
                monedaOrigen = dolar;
                monedaDestino = pesoColombiano;
                break;
            case 6:
                monedaOrigen = pesoColombiano;
                monedaDestino = dolar;
                break;
        }

        // URI para solicitar los datos de conversión
        URI urlSolicitud = URI.create("https://v6.exchangerate-api.com/v6/" + claveApi + "/pair/" + monedaOrigen + "/" + monedaDestino + "/" + montoAConvertir);

        HttpClient clienteHttp = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder().uri(urlSolicitud).build();

        try {
            HttpResponse<String> respuesta = clienteHttp.send(solicitud, HttpResponse.BodyHandlers.ofString());

            // Usar Gson para analizar la respuesta JSON y extraer el resultado
            Monedas resultado = new Gson().fromJson(respuesta.body(), Monedas.class);
            resultadoConversion = resultado.conversion_result();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la conversión: " + e.getMessage());
        }
    }

    public String obtenerMensajeResultado() {
        return "El monto de " + montoAConvertir + " [" + monedaOrigen + "] " +
               "es equivalente a: " + String.format("%.4f", resultadoConversion) + " [" + monedaDestino + "]";
    }
}
