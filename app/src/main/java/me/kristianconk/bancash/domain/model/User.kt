package me.kristianconk.bancash.domain.model

/**
 * Representa un usuario registrado en la aplicacion
 */
data class User(
    val id:String,
    val username:String, //name and lastName
    val avatarUrl: String? = null,
    val state:UserState
)

/**
 * Posibles estados de un usuario
 */
enum class UserState{
    ACTIVE,
    BLOCKED,
    INACTIVE,
    UNKNOWN
}