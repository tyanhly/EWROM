package io.vcode.eworm.entity

class RAction[TACL](pname: String, pvalue: TACL) extends TAction[TACL] {
    this.name = pname
    this.value = pvalue
}

object RAction {

    def getInstance[TACL](pname: String, pvalue: TACL): RAction[TACL] = {
        return new RAction[TACL](pname, pvalue);
    }

}
