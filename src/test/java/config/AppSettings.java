package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * config.json dosyasını okuyan ve ayarları statik olarak sağlayan sınıf.
 * Sınıf yüklendiğinde (static blok) ayarları bir kez okur.
 */
public class AppSettings {

    private static final Logger logger = LoggerFactory.getLogger(AppSettings.class);
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/src/config.json";
    private static JSONObject config;
    private static String platform;

    static {
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            JSONParser parser = new JSONParser();
            config = (JSONObject) parser.parse(reader);
            platform = (String) config.get("platform");
            logger.info("Konfigürasyon yüklendi. Platform: {}", platform);

        } catch (FileNotFoundException e) {
            logger.error("HATA: {} dosyası bulunamadı!", CONFIG_FILE_PATH, e);
            throw new RuntimeException(CONFIG_FILE_PATH + " dosyası bulunamadı.", e);
        } catch (IOException | ParseException e) {
            logger.error("HATA: {} dosyası okunurken hata oluştu!", CONFIG_FILE_PATH, e);
            throw new RuntimeException(CONFIG_FILE_PATH + " okunurken hata.", e);
        }
    }

    /**
     * Aktif platformu (android/ios) config.json'dan döndürür.
     * @return Platform adı (String)
     */
    public static String getPlatform() {
        return platform;
    }

    /**
     * Aktif platforma ait olan tüm yetenekleri (capabilities)
     * JSONObject olarak döndürür.
     * @return Capabilities (JSONObject)
     */
    public static JSONObject getCapabilities() {
        return (JSONObject) config.get(platform);
    }

    private AppSettings() {
    }
}