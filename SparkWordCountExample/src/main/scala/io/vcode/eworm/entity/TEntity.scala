package io.vcode.eworm.entity

trait TEntity[TAttr <: TAttribute, TAct <: TAction] extends TBaseEntity{
    var attributes: Array[TAttr]
    var actions: Array[TAct]
}
