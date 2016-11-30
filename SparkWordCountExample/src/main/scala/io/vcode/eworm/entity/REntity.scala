package io.vcode.eworm.entity

import sun.security.jca.GetInstance

class REntity(pname: String, attrs: Array[RAttribute[_]], acts: Array[RAction[_]])
    extends TEntity[RAttribute[_], RAction[_]]{
    
    def this(pname: String, attrs: Array[RAttribute[_]]) = this(pname, attrs, null.asInstanceOf[Array[RAction[_]]])
    def this(pname: String, acts: Array[RAction[_]]) = this(pname, null.asInstanceOf[Array[RAttribute[_]]],acts)
    this.name = pname;
    this.actions = acts
    this.attributes = attrs
}

object REntity {
    def getInstance(pname: String, attrs: Array[RAttribute[_]], acts: Array[RAction[_]]): REntity = {
        return new REntity(pname,attrs, acts);
    }
    def getInstance(pname: String, attrs: Array[RAttribute[_]]): REntity = {
        return new REntity(pname, attrs);
    }
    def getInstance(pname: String, acts: Array[RAction[_]]): REntity = {
        return new REntity(pname, acts);
    }
}
