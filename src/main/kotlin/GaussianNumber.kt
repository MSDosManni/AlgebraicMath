import kotlin.math.roundToInt

class GaussianNumber(val real: Int, val imaginary: Int) : EuclideanRingElement {

    override fun divisionWithRemainder(b: EuclideanRingElement): DivisionResult {
        if (b !is GaussianNumber) throw IllegalStateException("$b must be a GaussianNumber")
        val conjProd: GaussianNumber = (this * GaussianNumber(b.real, -b.imaginary)) as GaussianNumber
        val real = (conjProd.real.toFloat() / (b.real*b.real + b.imaginary*b.imaginary)).roundToInt()
        val imaginary = (conjProd.imaginary.toFloat() / (b.real*b.real + b.imaginary*b.imaginary)).roundToInt()
        val quotient = GaussianNumber(real, imaginary)
        val rest = this - b*quotient
        return DivisionResult(quotient, rest)
    }

    override fun getOne(): EuclideanRingElement {
        return GaussianNumber(1,0)
    }

    override fun getZero(): EuclideanRingElement {
        return GaussianNumber(0,0)
    }

    override fun inverse(): EuclideanRingElement {
        if (!isUnit()) throw IllegalStateException("Cant invert $this")
        if (real==0) {
            return if (imaginary==1) GaussianNumber(0,-1) else GaussianNumber(0,1)
        } else {
            return if (real==1) GaussianNumber(1,0) else GaussianNumber(-1,0)
        }
    }

    override fun unaryMinus(): EuclideanRingElement {
        return GaussianNumber(-real,-imaginary)
    }

    override fun plus(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is GaussianNumber) throw IllegalStateException("$other must be a GaussianNumber")
        return GaussianNumber(other.real+real, other.imaginary+imaginary)
    }

    override fun minus(other: EuclideanRingElement): EuclideanRingElement {
        return this + (-other)
    }

    override fun times(other: EuclideanRingElement): EuclideanRingElement {
        if (other !is GaussianNumber) throw IllegalStateException("$other must be a GaussianNumber")
        return GaussianNumber(real*other.real - imaginary*other.imaginary, real*other.imaginary + imaginary*other.real)
    }

    override fun isUnit(): Boolean {
        return (NumberElement(real).isUnit() && imaginary==0) || (NumberElement(imaginary).isUnit() && real==0)
    }

    override fun isZero(): Boolean {
        return real==0 && imaginary==0
    }

    override fun toString(): String {
        return "$real + $imaginary *i"
    }
}