package layouts.components.forms

import helpers.RequestManager

open class GliderForm : RequestManager() {

    protected var errorTriggered: MutableList<Boolean> = mutableListOf(false, false)

}