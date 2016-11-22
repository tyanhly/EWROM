package io.vcode.eworm.entity

trait TRelation[TAttr <: TAttribute, TAct <: TAction, TE <: TEntity[TAttr, TAct]] extends TBaseEntity{
    var origin: TE
    var target: TE
    var attributes: Array[TAttr]
    var canSwap: Boolean
}
