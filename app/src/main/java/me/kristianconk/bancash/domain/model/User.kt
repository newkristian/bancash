package me.kristianconk.bancash.domain.model

data class User(
    val id:String,
    val username:String,
    val state:UserState
)

enum class UserState{
    ACTIVE,
    BLOCKED,
    INACTIVE,
    UNKNOWN
}