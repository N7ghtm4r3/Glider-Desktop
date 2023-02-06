package helpers;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.glider.records.Session;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class User extends Session {

    public static User user;

    private static final Preferences shared;

    static {
        try {
            shared = new Preferences();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor to init {@link Session} object <br>
     * No-any params required
     *
     * @apiNote this constructor will be invoked during the normal Glider's workflow
     **/
    public User() {
        super(shared.getString(SessionKeys.token.name(), null),
                shared.getString(SessionKeys.ivSpec.name(), null),
                shared.getString(SessionKeys.secretKey.name(), null),
                shared.getString(SessionKeys.sessionPassword.name(), null),
                shared.getString(SessionKeys.hostAddress.name(), null),
                shared.getInt(SessionKeys.hostPort.name(), -1),
                shared.getBoolean(SessionKeys.singleUseMode.name(), true),
                shared.getBoolean(SessionKeys.QRCodeLoginEnabled.name(), false),
                shared.getBoolean(SessionKeys.runInLocalhost.name(), true));
    }

    private final static class Preferences {

        private final JsonHelper prefs;

        public Preferences() throws IOException {
            final String fPath = "/gliderprefs.json";
            final String path = Preferences.class.getResource(fPath).getFile();
            File file = null;
            boolean createFile = path == null;
            if (createFile) {
                try (FileWriter writer = new FileWriter(fPath)) {
                    writer.write("{}");
                }
            } else
                file = new File(fPath);
            prefs = new JsonHelper(new JSONObject(new Scanner(file).useDelimiter("\\Z").next()));
        }

        public String getString(String key, String defValue) {
            return prefs.getString(key, defValue);
        }

        public int getInt(String key, int defValue) {
            return prefs.getInt(key, defValue);
        }

        public boolean getBoolean(String key, boolean defValue) {
            return prefs.getBoolean(key, defValue);
        }

    }

}
