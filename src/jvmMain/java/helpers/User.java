package helpers;

import com.tecknobit.glider.records.GliderRecord;
import com.tecknobit.glider.records.Session;

import java.util.prefs.Preferences;

/**
 * The {@link User} is class useful to store all the information for a {@code Glider}'s user
 * allowing the correct workflow
 *
 * @author Tecknobit - N7ghtm4r3
 * @see GliderRecord
 * @see Session
 **/
public class User extends Session {

    /**
     * {@code prefs} instance to manage the information of the user
     **/
    private static final Preferences prefs = Preferences.userRoot().node("/user/glider");

    /**
     * {@code user} instance to manage statically the session of the user
     **/
    public static User user;

    /**
     * Constructor to init {@link Session} object <br>
     * No-any params required
     *
     * @apiNote this constructor will be invoked during the normal Glider's workflow
     **/
    public User() {
        super(prefs.get(SessionKeys.token.name(), null),
                prefs.get(SessionKeys.ivSpec.name(), null),
                prefs.get(SessionKeys.secretKey.name(), null),
                prefs.get(SessionKeys.sessionPassword.name(), null),
                prefs.get(SessionKeys.hostAddress.name(), null),
                prefs.getInt(SessionKeys.hostPort.name(), -1),
                prefs.getBoolean(SessionKeys.singleUseMode.name(), true),
                prefs.getBoolean(SessionKeys.QRCodeLoginEnabled.name(), false),
                prefs.getBoolean(SessionKeys.runInLocalhost.name(), true));
    }

}
