import java.lang.IllegalStateException

object ChineseRemainderTheorem {
    fun solveProblem(congruences: Array<Congruence>) : Congruence {
        val one = congruences[0].result.getOne()
        val zero = congruences[0].result.getZero()

        val N = Array(congruences.size) {
            var prod = one
            for (i in congruences.indices) {
                if (i!=it) {
                    prod *= congruences[i].modulo
                }
            }
            prod
        }

        val y = Array(congruences.size) {
            val res = gcd(N[it], congruences[it].modulo)
            if (!res.gcd.isUnit()) throw IllegalStateException("This should not happen. GCD res of ${N[it]} with ${congruences[it].modulo} is ${res.gcd}")
            res.a*res.gcd.inverse()
        }

        var solution = zero
        for (i in congruences.indices) {
            solution += congruences[i].result * y[i] * N[i]
        }
        val NProd = congruences.map {it.modulo}.fold(one) {c,r -> c*r}
        return Congruence(solution.divisionWithRemainder(NProd).r, NProd)
    }
}

data class Congruence(val result: EuclideanRingElement, val modulo: EuclideanRingElement)