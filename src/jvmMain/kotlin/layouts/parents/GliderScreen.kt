package layouts.parents

import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.records.Device.DeviceKeys
import com.tecknobit.glider.records.Device.Type.DESKTOP
import com.tecknobit.glider.records.Session.SessionKeys
import helpers.User.user
import org.json.JSONObject
import java.net.InetAddress

/**
 * **GliderScreen** is the super class where all the views inheriting are enabled to perform a request with your
 * own **Glider's** backend service
 *
 * @author Tecknobit - N7ghtm4r3
 * **/
open class GliderScreen {

    /**
     * **DEVICE_NAME** -> the name of the device
     */
    private val DEVICE_NAME: String = InetAddress.getLocalHost().hostName

    /**
     * **payload** -> the payload of the request to perform
     */
    protected var payload: JSONObject? = JSONObject()

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

}