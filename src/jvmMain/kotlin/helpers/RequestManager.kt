package helpers

import com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm.CBC_ALGORITHM
import com.tecknobit.apimanager.apis.sockets.SocketManager.StandardResponseCode.*
import com.tecknobit.apimanager.apis.sockets.encrypteds.AESSocketManager
import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.helpers.GliderLauncher.GliderKeys.statusCode
import com.tecknobit.glider.records.Device.DeviceKeys
import com.tecknobit.glider.records.Device.Type.DESKTOP
import com.tecknobit.glider.records.Session.SessionKeys
import helpers.User.Companion.user
import org.json.JSONObject
import java.net.InetAddress

/**
 * **RequestManager** is the super class where all the views inheriting are enabled to perform a request with your
 * own **Glider's** backend service
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
open class RequestManager {

    /**
     * **DEVICE_NAME** -> the name of the device
     */
    private val DEVICE_NAME: String = InetAddress.getLocalHost().hostName

    /**
     * **payload** -> the payload of the request to perform
     */
    protected var payload: JSONObject? = JSONObject()

    companion object {

        /**
         * **socketManager** -> the manager of the requests
         */
        var socketManager: AESSocketManager? =
            if (user.token != null)
                AESSocketManager(user.hostAddress, user.hostPort, user.ivSpec, user.secretKey, CBC_ALGORITHM)
            else
                null

        /**
         * **response** -> the response result of the requests
         */
        var response: JSONObject = JSONObject()

    }

    /**
     * Method to create the payload for a request
     *
     * @param operation the operation to perform
     * @param params dynamic params list to attach to the [payload]
     */
    protected open fun <T> setRequestPayload(operation: GliderLauncher.Operation?, vararg params: T) {
        if (payload == null)
            payload = JSONObject()
        else
            payload!!.clear()
        payload!!.put(GliderLauncher.GliderKeys.ope.name, operation)
        payload!!.put(DeviceKeys.name.name, DEVICE_NAME)
            .put(DeviceKeys.type.name, DESKTOP)
            .put(SessionKeys.sessionPassword.name, user.sessionPassword)
    }

    /**
     * Method to get whether the request has got a successful response status. No-any params required
     *
     * @return whether the request has got a successful response status
     */
    fun successfulResponse(): Boolean {
        return response.getString(statusCode.name) == SUCCESSFUL.name
    }

    /**
     * Method to get whether the request has got a generic response status. No-any params required
     *
     * @return whether the request has got a generic response status
     */
    fun genericResponse(): Boolean {
        return response.getString(statusCode.name) == GENERIC_RESPONSE.name
    }

    /**
     * Method to get whether the request has got a failed response status. No-any params required
     *
     * @return whether the request has got a failed response status
     */
    fun failedResponse(): Boolean {
        return response.getString(statusCode.name) == FAILED.name
    }

}