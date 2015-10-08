package grafika;

import java.io.IOException;
import java.util.Properties;

public class Propiedades extends Properties {

    public Propiedades(String idioma) {
        if (idioma.equals("ESPANOL")) {//si es español
            //lee las propiedades del archiv Ingles.properties
            getProperties("Espanol.properties");
        } else if (idioma.equals("INGLES")) {//si es Ingles
            //lee las propiedades del archiv Ingles.properties
            getProperties("Ingles.properties");
        } else {//sino por default idioma español
            getProperties("ESPANOL");
        }
    }
    /* se leen las propiedades */
    private void getProperties(String idioma) {
        try {
            this.load(getClass().getResourceAsStream(idioma));
        } catch (IOException ex) {
        }
    }
}
