package io.vcode.eworm.entity

trait TBaseEntity extends Serializable{
    @transient
    private var _name: String = null
}
