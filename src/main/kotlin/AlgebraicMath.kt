fun main() {

    val m1 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 1,1))
    val m2 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 1,0,1))
    val m3 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 2,0,1,1))

    val r1 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 1))
    val r2 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 2,1))
    val r3 = Polynom(ZModuloM(0,3), ZModuloM(1,3),
        ZModuloM.fromArray(3, 0,1,1))

    val congruences = arrayOf(Congruence(r1,m1), Congruence(r2,m2), Congruence(r3,m3))

    val solution = ChineseRemainderTheorem.solveProblem(congruences)
    println(solution)

}