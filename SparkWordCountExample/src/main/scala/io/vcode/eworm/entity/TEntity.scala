package io.vcode.eworm.entity

trait TEntity[TATTRIBUTE, TACTION]
     extends TBaseEntity{
    var name: String = _
    var attributes: Array[TATTRIBUTE] = _
    var actions: Array[TACTION] = _
}
