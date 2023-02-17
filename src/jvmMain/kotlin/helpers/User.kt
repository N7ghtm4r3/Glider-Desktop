package helpers

import com.tecknobit.glider.records.Session
import java.util.prefs.BackingStoreException
import java.util.prefs.Preferences

/**
 * The [User] is class useful to store all the information for a `Glider`'s user
 * allowing the correct workflow
 *
 * @author Tecknobit - N7ghtm4r3
 *
 * @see Session
 */
class User : Session(
    prefs[SessionKeys.token.name, null],
    prefs[SessionKeys.ivSpec.name, null],
    prefs[SessionKeys.secretKey.name, null],
    prefs[SessionKeys.sessionPassword.name, null],
    prefs[SessionKeys.hostAddress.name, null],
    prefs.getInt(SessionKeys.hostPort.name, -1),
    prefs.getBoolean(SessionKeys.singleUseMode.name, true),
    prefs.getBoolean(SessionKeys.QRCodeLoginEnabled.name, false),
    prefs.getBoolean(SessionKeys.runInLocalhost.name, true)
) {

    companion object {

        /**
         * `prefs` instance to manage the information of the user
         */
        val prefs: Preferences = Preferences.userRoot().node("/user/glider")

        /**
         * `user` instance to manage statically the session of the user
         */
        val user: User = User()

    }

    /**
     * Method to clear the user session. No-any params required
     */
    fun clearUserSession() {
        try {
            prefs.clear()
        } catch (e: BackingStoreException) {
            throw RuntimeException(e)
        }
    }

}