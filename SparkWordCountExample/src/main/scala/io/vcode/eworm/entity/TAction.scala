package io.vcode.eworm.entity

trait TAction[TACL] extends TBaseEntity{
    var name: String = _
    var value: TACL = _
}
