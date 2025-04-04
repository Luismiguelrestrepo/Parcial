import java.io.FileReader;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class GestorJSON {
    private static final String RUTA_JSON = "src\\FrasesTraducidas.json";
    private static List<String> frases = new ArrayList<>();
    private static Map<String, Map<String, String>> traducciones = new HashMap<>();

    public static void cargarFrases() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(RUTA_JSON));
            JSONArray frasesArray = (JSONArray) jsonObject.get("Frases");
    
            System.out.println(" Cargando frases desde JSON...");
    
            for (Object obj : frasesArray) {
                JSONObject fraseObj = (JSONObject) obj;
                String texto = (String) fraseObj.get("Texto");
                frases.add(texto);
                System.out.println(" Frase cargada: " + texto);
    
                JSONArray traduccionesArray = (JSONArray) fraseObj.get("Traducciones");
                Map<String, String> mapaTraducciones = new HashMap<>();
                for (Object tradObj : traduccionesArray) {
                    JSONObject traduccion = (JSONObject) tradObj;
                    String idioma = (String) traduccion.get("Idioma");
                    String textoTraducido = (String) traduccion.get("TextoTraducido");
                    mapaTraducciones.put(idioma, textoTraducido);
                }
                traducciones.put(texto, mapaTraducciones);
            }
    
            System.out.println("Total frases cargadas: " + frases.size());
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public static List<String> getFrases() {
        return frases;
    }

    public static Map<String, String> getTraducciones(String frase) {
        return traducciones.getOrDefault(frase, new HashMap<>());
    }
}
