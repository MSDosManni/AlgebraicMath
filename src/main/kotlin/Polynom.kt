import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.Integer.max
import java.lang.StringBuilder

class Polynom(val zeroInRing: EuclideanRingElement, val oneInRing: EuclideanRingElement, val coefficients: Array<EuclideanRingElement>) : EuclideanRingElement {
    

    override fun divisionWithRemainder(b: EuclideanRingElement): DivisionResult {
        if (b !is Polynom) throw IllegalArgumentException("$b is not of type Polynom")
        var rest = this
        var quotient = getZero()
        val bLC = b.coefficients.last()
        val bLCInv = bLC.inverse()
        while (rest.coefficients.size >= b.coefficients.size ) {
            val coeffNum = rest.coefficients.size - b.coefficients.size
            val coeff = rest.coefficients.last() * bLCInv
            val coeffArr = Array(coeffNum+1) {
                if (it==coeffNum) coeff else zeroInRing
            }
            val helpPoly = Polynom(zeroInRing, oneInRing, coeffArr)
            quotient+=helpPoly
            val subtraction: Polynom = (helpPoly * b) as Polynom
            rest  = (rest - subtraction) as Polynom
        }
        return DivisionResult(quotient, rest)
    }

    override fun getOne(): EuclideanRingElement {
        return Polynom(zeroInRing,oneInRing, arrayOf(oneInRing))
    }

    override fun getZero(): EuclideanRingElement {
        return Polynom(zeroInRing, oneInRing, arrayOf())
    }

    override fun isUnit(): Boolean {
        return coefficients.size == 1 && coefficients[0].isUnit()
    }

    override fun inverse(): Polynom {
        if (!isUnit()) throw IllegalStateException("Can't invert $this")
        return Polynom(zeroInRing, oneInRing, arrayOf(coefficients[0].inverse()))
    }

    override fun plus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is Polynom) throw IllegalArgumentException("$other is not of type Polynom")
        if (other.coefficients.size > coefficients.size) return other + this

        val newCoeffs = Array(coefficients.size) {
            getCoefficient(it) + other.getCoefficient(it)
        }

        var i = newCoeffs.size - 1
        while (i>=0 && newCoeffs[i].isZero()) i--
        val trueNewCoeffs = Array(i+1) {
            newCoeffs[it]
        }
        return Polynom(zeroInRing, oneInRing, trueNewCoeffs)
    }

    override fun minus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is Polynom) throw IllegalArgumentException("$other is not of type Polynom")
        return this + (-other)
    }

    override fun unaryMinus(): EuclideanRingElement {
        return Polynom(zeroInRing, oneInRing, coefficients.map {-it }.toTypedArray())
    }

    override fun times(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is Polynom) throw IllegalArgumentException("$other is not of type Polynom")
        val newCoeffs = Array(max(0,(coefficients.size-1) + (other.coefficients.size-1) + 1)) {
            var sum = zeroInRing
            for (i in 0..it) {
                sum += (getCoefficient(i) * other.getCoefficient(it-i))
            }
            sum
        }
        return Polynom(zeroInRing, oneInRing, newCoeffs)
    }

    override fun isZero(): Boolean {
        return coefficients.size == 0
    }

    fun getCoefficient(i: Int): EuclideanRingElement {
        if (i<coefficients.size) return coefficients[i] else return zeroInRing
    }

    override fun toString() : String {
        val builder = StringBuilder("(")
        for (i in coefficients.size-1 downTo 1) {
            if (!coefficients[i].isZero())
            builder.append(getCoefficient(i).toString()+"t^"+i +" + ")
        }
        if (coefficients.size==0) builder.append(zeroInRing)
        if (coefficients.size > 0) builder.append(getCoefficient(0).toString())
        builder.append(")")
        return builder.toString()
    }

    fun insert(element: EuclideanRingElement) : EuclideanRingElement {
        var sum = element.getZero()
        for (i in 0 until coefficients.size) {
            var prod = element.getOne()
            for (j in 0 until i) {
                prod = prod*element
            }
            sum += getCoefficient(i) * prod
        }
        return sum
    }
}