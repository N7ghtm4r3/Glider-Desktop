package layouts.parents

import com.tecknobit.glider.helpers.GliderLauncher
import com.tecknobit.glider.records.Device
import com.tecknobit.glider.records.Device.DeviceKeys
import com.tecknobit.glider.records.Session.SessionKeys
import helpers.User
import org.json.JSONObject

open class GliderScreen {
    protected var payload: JSONObject = JSONObject()
    protected open fun <T> setRequestPayload(operation: GliderLauncher.Operation?, vararg params: T): JSONObject? {
        payload.clear()
        payload.put(GliderLauncher.GliderKeys.ope.name, operation)
        payload.put(DeviceKeys.name.name, "DEVICE_NAME")
            .put(DeviceKeys.type.name, Device.Type.DESKTOP)
            .put(SessionKeys.sessionPassword.name, User.user.sessionPassword)
        return payload
    }
}