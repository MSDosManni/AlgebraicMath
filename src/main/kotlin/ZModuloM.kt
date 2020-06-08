class ZModuloM(pNumber: Int, val modulo: Int) : EuclideanRingElement {

    companion object {
        fun fromArray(mod: Int, vararg values: Int) : Array<EuclideanRingElement> {
            return Array(values.size) {
                ZModuloM(values[it], mod)
            }
        }

    }

    val number: Int
    init {
        var testNumber = pNumber
        while (testNumber < 0) testNumber += modulo
        number = testNumber % modulo
    }

    override fun divisionWithRemainder(b: EuclideanRingElement): DivisionResult {
        if (b.isZero()) return DivisionResult(ZModuloM(0,modulo), this)
        return DivisionResult(this * b.inverse(), getZero())
    }

    override fun getOne(): EuclideanRingElement {
        return ZModuloM(1,modulo)
    }

    override fun getZero(): EuclideanRingElement {
        return ZModuloM(0,modulo)
    }

    override fun inverse(): EuclideanRingElement {
        if (isUnit()) {
            val gcdRes = gcd(NumberElement(number), NumberElement(modulo))
            val inverseNum = gcdRes.a * gcdRes.gcd.inverse()
            if (inverseNum is NumberElement)
                return ZModuloM(inverseNum.value, modulo)
        }

        throw IllegalArgumentException("$this is not a unit thus cannot be inverted")
    }

    override fun unaryMinus(): EuclideanRingElement {
        return ZModuloM(-number, modulo)
    }

    override fun plus(other: EuclideanRingElement): EuclideanRingElement {
       if (other !is ZModuloM) throw IllegalArgumentException("$other is not element of ZModuloM")
        return ZModuloM((other.number + number) % modulo, modulo)
    }

    override fun minus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is ZModuloM) throw IllegalArgumentException("$other is not element of ZModuloM")

        return this + (-other)
    }

    override fun times(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is ZModuloM) throw IllegalArgumentException("$other is not element of ZModuloM")
        return ZModuloM((other.number * number) % modulo, modulo)
    }

    override fun isUnit(): Boolean {
        val gcdResult =  gcd(NumberElement(number), NumberElement(modulo))
        if (gcdResult.gcd is NumberElement) {
            return gcdResult.gcd.isUnit()
        }
        return false
    }

    override fun isZero(): Boolean {
        return number%modulo==0
    }

    override fun toString(): String {
        return number.toString()
    }
}