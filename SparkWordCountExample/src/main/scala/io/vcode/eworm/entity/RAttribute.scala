package io.vcode.eworm.entity

class RAttribute[TATL](pname: String, pvalue: TATL) extends TAttribute[TATL] {
    this.name = pname
    this.value = pvalue
}

object RAttribute {
    def getInstance[TATL](pname: String, pvalue: TATL): RAttribute[TATL] = {
        return new RAttribute[TATL](pname, pvalue);
    }
}
