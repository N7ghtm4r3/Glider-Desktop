package helpers

import androidx.compose.runtime.mutableStateListOf
import com.tecknobit.glider.records.Device
import com.tecknobit.glider.records.Device.DevicePermission
import com.tecknobit.glider.records.Password
import com.tecknobit.glider.records.Session
import com.tecknobit.glider.records.Session.SessionKeys.*
import org.json.JSONObject
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
    prefs[token.name, null],
    prefs[ivSpec.name, null],
    prefs[secretKey.name, null],
    prefs[sessionPassword.name, null],
    prefs[hostAddress.name, null],
    prefs.getInt(hostPort.name, -1),
    prefs.getBoolean(singleUseMode.name, true),
    prefs.getBoolean(QRCodeLoginEnabled.name, false),
    prefs.getBoolean(runInLocalhost.name, true)
) {

    companion object {

        /**
         * `prefs` instance to manage the information of the user
         */
        val prefs: Preferences = Preferences.userRoot().node("/user/glider")

        /**
         * `user` instance to manage statically the session of the user
         */
        var user: User = User()

        /**
         * `permission` the current user permission
         */
        lateinit var permission: DevicePermission

        /**
         * **passwords** -> list of the [Password] of the session
         */
        val passwords: MutableList<Password> = mutableStateListOf()

        /**
         * **devices** -> list of the [Device] connected to the session
         */
        val devices: MutableList<Device> = mutableStateListOf()

    }

    /**
     * Method to insert a new session data
     * @param sessionData: data of the session to save
     * @throws Exception when an error occurred
     */
    fun insertUserSession(sessionData: JSONObject) {
        for (key: String in sessionData.keySet())
            prefs.put(key, sessionData.get(key).toString())
        user = User()
    }

    /**
     * Method to clear the user session. No-any params required
     */
    fun clearUserSession() {
        try {
            prefs.clear()
            passwords.clear()
            devices.clear()
            user = User()
        } catch (e: BackingStoreException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Method to check whether a device has the [DevicePermission.ADMIN] or
     * [DevicePermission.PASSWORD_MANAGER] permissions <br></br>
     * No-any params required
     *
     * @return whether a device has the right permission
     */
    fun isPasswordManager(): Boolean {
        return permission == DevicePermission.PASSWORD_MANAGER || isAdmin()
    }

    /**
     * Method to check whether a device has the [DevicePermission.ADMIN] or
     * [DevicePermission.ACCOUNT_MANAGER] permissions <br></br>
     * No-any params required
     *
     * @return whether a device has the right permission
     */
    fun isAccountManager(): Boolean {
        return permission == DevicePermission.ACCOUNT_MANAGER || isAdmin()
    }

    /**
     * Method to check whether a device has the [DevicePermission.ADMIN] permission <br></br>
     * No-any params required
     *
     * @return whether a device has the right permission
     */
    fun isAdmin(): Boolean {
        return permission == DevicePermission.ADMIN
    }

}