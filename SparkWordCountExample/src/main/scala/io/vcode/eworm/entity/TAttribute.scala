package io.vcode.eworm.entity

trait TAttribute[TATL] extends TBaseEntity{
    var name: String = _
    var value: TATL = _
}
