interface EuclideanRingElement {
    fun divisionWithRemainder(b: EuclideanRingElement): DivisionResult

    fun getOne() : EuclideanRingElement
    fun getZero(): EuclideanRingElement
    fun inverse(): EuclideanRingElement
    operator fun unaryMinus() : EuclideanRingElement
    operator fun plus(other: EuclideanRingElement) : EuclideanRingElement
    operator fun minus(other: EuclideanRingElement) : EuclideanRingElement
    operator fun times(other: EuclideanRingElement) : EuclideanRingElement
    fun isUnit(): Boolean



    fun isZero() : Boolean
}

data class DivisionResult(val q: EuclideanRingElement, val r: EuclideanRingElement)
/**
 * Represents the result of the function gcd(x,y) in the way that gcd = ax + by
 */
data class GCDResult(val gcd: EuclideanRingElement, val a: EuclideanRingElement, val b: EuclideanRingElement)

fun gcd(a: EuclideanRingElement, b: EuclideanRingElement): GCDResult {
    val rChain = mutableListOf(a, b)
    val qChain = mutableListOf<EuclideanRingElement>()

    var stop = false
    while (!stop) {
        val divRes = rChain[rChain.lastIndex - 1].divisionWithRemainder(rChain.last())
        rChain.add(divRes.r)
        qChain.add(divRes.q)
        if (divRes.r.isZero()) stop = true
    }
    val factors = mutableListOf(Pair(a.getOne(), a.getZero()), Pair(a.getZero(), a.getOne()))
    for (i in 2 until rChain.size - 1) {
        val aValue = factors[i - 2].first - qChain[i - 2] * factors[i - 1].first
        val bValue = factors[i-2].second - qChain[i-2] * factors[i-1].second
        factors.add(Pair(aValue as EuclideanRingElement,bValue as EuclideanRingElement))
        //println("${rChain[i]}=$a * $aValue + $b * $bValue")
    }
    return GCDResult(rChain[rChain.size-2], factors.last().first, factors.last().second)
}