package expo.modules.updates.loader

import expo.modules.jsonutils.getNullable
import expo.modules.jsonutils.require
import expo.modules.updates.UpdatesUtils
import expo.modules.updates.manifest.ResponseHeaderData
import expo.modules.updates.manifest.UpdateManifest
import org.json.JSONObject
import java.util.*

enum class UpdateMessageType {
    NO_UPDATE_AVAILABLE,
    ROLLBACK_TO_EMBEDDED
}

data class SigningInfo(val easProjectId: String?, val scopeKey: String?)

sealed class UpdateMessage(val type: UpdateMessageType, val signingInfo: SigningInfo?) {
    class NoUpdateAvailableUpdateMessage(signingInfo: SigningInfo?) : UpdateMessage(UpdateMessageType.NO_UPDATE_AVAILABLE, signingInfo)
    class RollbackToEmbeddedUpdateMessage(val commitTime: Date, signingInfo: SigningInfo?) : UpdateMessage(UpdateMessageType.ROLLBACK_TO_EMBEDDED, signingInfo)

    companion object {
        fun fromJSONString(jsonString: String): UpdateMessage {
            val json = JSONObject(jsonString)
            val signingInfo = json.getNullable<JSONObject>("signingInfo")?.let {
                SigningInfo(it.require("projectId"), it.require("scopeKey"))
            }
            return when (val messageType = json.require<String>("messageType")) {
                "noUpdateAvailable" -> NoUpdateAvailableUpdateMessage(signingInfo)
                "rollbackToEmbedded" -> RollbackToEmbeddedUpdateMessage(
                    UpdatesUtils.parseDateString(json.require<JSONObject>("messagePayload").require("commitTime")),
                    signingInfo
                )
                else -> throw Error("Invalid message messageType: $messageType")
            }
        }
    }
}

data class UpdateResponse(
    val responseHeaderData: ResponseHeaderData?,
    val manifestUpdateResponsePart: UpdateResponsePart.ManifestUpdateResponsePart?,
    val messageUpdateResponsePart: UpdateResponsePart.MessageUpdateResponsePart?
)

sealed class UpdateResponsePart {
    data class ManifestUpdateResponsePart(val updateManifest: UpdateManifest) : UpdateResponsePart()
    data class MessageUpdateResponsePart(val updateMessage: UpdateMessage) : UpdateResponsePart()
}