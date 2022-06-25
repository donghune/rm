package entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Textures(
    @SerialName("layer0")
    val layer0: String
)