package entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceModel(
    @SerialName("overrides")
    val overrides: List<OverrideEntity>,
    @SerialName("parent")
    val parent: String,
    @SerialName("textures")
    val textures: Textures
)