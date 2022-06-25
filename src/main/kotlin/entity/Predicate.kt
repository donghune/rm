package entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Predicate(
    @SerialName("custom_model_data")
    val customModelData: Int
)