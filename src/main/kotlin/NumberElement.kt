import java.lang.IllegalArgumentException

class NumberElement(val value: Int) : EuclideanRingElement {

    companion object {
        fun arrToNumbers(arr: IntArray): Array<EuclideanRingElement> {
            return arr.map {NumberElement(it)}.toTypedArray()
        }
    }

    override fun divisionWithRemainder(b: EuclideanRingElement): DivisionResult {
        if (b !is NumberElement) throw IllegalArgumentException("$b must be of type NumberElement but is ${b.javaClass}")
        return DivisionResult(NumberElement(this.value / b.value), NumberElement(this.value % b.value))
    }

    override fun getOne(): EuclideanRingElement {
        return NumberElement(1)
    }

    override fun getZero(): EuclideanRingElement {
        return NumberElement(0)
    }

    override fun isUnit(): Boolean {
        return value==1 || value == -1
    }

    override fun inverse(): EuclideanRingElement {
        return if (value==1) getOne() else NumberElement(-1)
    }

    override fun plus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is NumberElement) throw IllegalArgumentException("$other must be of type NumberElement but is ${other.javaClass}")
        return NumberElement(value+other.value)
    }

    override fun minus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is NumberElement) throw IllegalArgumentException("$other must be of type NumberElement but is ${other.javaClass}")
        return NumberElement(value-other.value)
    }

    override fun unaryMinus(): EuclideanRingElement {
        return NumberElement(-value)
    }

    override fun times(other: EuclideanRingElement): EuclideanRingElement {
        if (other is Polynom) {
            return Polynom(NumberElement(0), NumberElement(1), Array(other.coefficients.size) {
                other.getCoefficient(it) * this
            })
        }
        if (other !is NumberElement) throw IllegalArgumentException("$other must be of type NumberElement but is ${other.javaClass}")
        return NumberElement(value*other.value)
    }

    override fun isZero(): Boolean {
        return value==0
    }

    override fun toString(): String {
        return value.toString()
    }

}