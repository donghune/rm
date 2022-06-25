package entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverrideEntity(
    @SerialName("model")
    val model: String,
    @SerialName("predicate")
    val predicate: Predicate
)