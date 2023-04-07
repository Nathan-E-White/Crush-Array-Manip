import org.jetbrains.annotations.Contract
import java.io.File
import java.io.IOException
import java.util.function.IntToLongFunction
import kotlin.system.exitProcess


/**
 * Annotation to decorate top level functions
 *
 * @author  Nathan E White, PhD
 * @version 1.0.0   07/19/2020
 * @see     <a href='https://kotlinlang.org/docs/annotations.html'>More Details</a>
 * @since   20
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class TopLevelFunction


/**
 * A function to print a <code>message</code> to the standard output stream when developing
 * code, with an easy way to turn the print statement off for production or
 * for testing versions.
 * <p>
 * The function should compile to dead bytecode if the
 * printing flag is set to false, so should be removed by the compiler if left
 * in the program without affecting runtime performance.
 *
 * @author  Nathan E White, PhD
 * @version 1.0.0   07/19/2020
 * @param   chkPrint    Boolean flag to check for developer mode
 * @param   message     Content to print if in developers mode
 * @return              <code>Unit</code> (void type for JVM).
 * @suppress            RedundantUnitReturnType
 * @suppress            unused
 * @throws              IOException
 * @see     <a href='https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/println.html'>JetBrains Documentation
 * for println</a>
 * @see     <a href='https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/'>JetBrains Documentation for Unit</a>
 * @since   20
 */
@TopLevelFunction
@Suppress("RedundantUnitReturnType", "unused")
@Contract(value = "(Boolean, Any?) -> Unit", pure = false)
fun devPrint(chkPrint: Boolean, message: Any?): Unit {
    if (chkPrint) {
        try {
            return println(message)
        } catch (e: IOException){
            System.err.print(e.message)
            exitProcess(-1)
        }
    }
}

/**
 *  Creates a <code>m</code>x<code>n</code> rank two tensor of <code>Int</code>
 *  types initialized to zero.
 *  <p>
 *  This is the 2D version of this overloaded function
 *
 * @author  Nathan E White, PhD
 * @version 1.0.0   07/19/2020
 * @param   m   <code>Int</code> which specifies <code>m</code> rows in the array
 * @param   n   <code>Int</code> which specifies <code>n</code> columns in the array
 * @return      Array<IntArray> data array initialized to zero
 * @suppress    FunctionName
 * @suppress    unused
 * @throws      ArrayIndexOutOfBoundsException
 * @see         Zeros(n: Int)
 * @since       20
 */
@Suppress("FunctionName")
@TopLevelFunction
fun Zeros(m: Int, n: Int): Array<IntArray> {
    if (m <= 0x0 || n <= 0x0) {
        throw ArrayIndexOutOfBoundsException("Tried to create a zero array with negative bounds.")
    }
    return Array(m) { IntArray(n, fun(_: Int): Int = 0x0) }
}

/**
 *  Creates a <code>n</code> length vector of <code>Int</code> types,
 *  initialized to zero.
 *  <p>
 *  This is the 1D version of the overloaded function.
 *
 * @author  Nathan E White, PhD
 * @version 1.0.0   07/19/2020
 * @param   n   <code>Int</code> which specifies <code>n</code> entries in the array
 * @return      Array<IntArray> data array initialized to zero
 * @suppress    FunctionName
 * @suppress    unused
 * @throws      ArrayIndexOutOfBoundsException
 * @see         Zeros(n: Int, m: Int)
 * @since       20
 */
@Suppress("FunctionName")
@TopLevelFunction
fun Zeros(n: Int): IntArray {
    return IntArray(n, fun(_: Int): Int = 0x0)
}

/**
 * An extension for the list type that provides the length of the list as a
 * simple getter
 *
 * @author      Nathan E White, PhD
 * @version     1.0.0   07/19/2020
 * @param T     Element type of the list
 * @return      length of the list
 * @suppress    unused
 * @since       20
 *
 */
@Suppress("unused")
val <T> List<T>.lastIndex: Int
    get() = this.size -1

/**
 * An extension for mutable list type that provides a swapping functionality for
 * two given indices in a type agnostic way.
 * <p>
 * Note the swap consumes a small amount of memory but otherwise happens in
 * place for the MutableList
 * </p>
 * 
 * @author      Nathan E White, PhD
 * @version     1.0.0   07/19/2020
 * @param       T       Type parameter of the underlying list
 * @param       index1  LHS/RHS position of entry to switch
 * @param       index2  RHS/LHS position of entry to switch
 * @throws      IndexOutOfBoundsException
 * @suppress    unused
 * 
 */
@Suppress("unused")
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this.set(index1, this.get(index2))
    this.set(index2, tmp)
}

const val devMode: Boolean = false

/**
 * Extension for the IntArray type to have a simple way to request the maximum
 * of the array. The max is computed by iterating through the array once.
 * Initially, the first value in the array is chosen, arbitrarily, and if any
 * larger value is spotted the larger value is substituted. 
 * 
 * @author  Nathan E White, PhD
 * @version     1.0.0   07/19/2020
 * @throws      IndexOutOfBoundsException
 * @suppress    unused
 * @suppress    ReplaceGetOrSet
 *
 */
@Suppress("ReplaceGetOrSet", "unused")
val IntArray.max: Int
    get() {
        /* Arbitrarily choose the first Int in the array */
        var tok: Int = this.get(0x0)
        for (idx in 1..this.size.minus(0x1)) {
            /* As we iterate through the list if we find a bigger number */
            /* then our placeholder, we copy the value into the placer   */
            val tmp: Int = this.get(idx)
            if (tmp > tok) {
                tok = tmp
            }
        }
        /* Token Int should now contain the max value */
        return tok
    }


fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {

    // Write your code here
    val qLen = queries.size

    /* Create a Qx3 array of zeros to work with */
    val calc = Zeros(qLen, 0x3)

    println("Qs = " + qLen.toString())

    /* Iterate through each query line */
    for (idx in 0..qLen.minus(0x1)) {

        var idxLHS = queries[idx][0]
        var idxRHS = queries[idx][1]
        var modVal = queries[idx][2]

        println(
            idxLHS.toString() + " " +
                    idxRHS.toString() + " " +
                    modVal.toString()
        )
    }

    return 0x0L
}


fun rowSum(row: Int, arr: Array<IntArray>) = arr[row].sum()
fun colSum(col: Int, arr: Array<IntArray>) = arr.sumOf { row -> row[col] }

fun main(args: Array<String>) {

    /**
     * @param fInp  The {@code File} holding the input text
     * @param fOut  The {@code File} to receive the output
     */
    data class FileSet(var fInp: File, var fOut: File)

    val ioSet = FileSet(File(args[0]), File(args[1]))

    // Contains constraints from the project requirements
    val constants = Constraints()

    // The length of the primary array to manipulate
    // TODO: Get these from the input file
    val n: Int = 0xA
    val q: Int = 0x3

    val data = IntArray(n, fun(_: Int): Int = 0x0)
    val quer = []


    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    /* Use the File.printWriter with Kt lambda */
    // TODO: Add locale
    ioSet.fOut.printWriter().use { out ->
        out.printf("%h%n", "")
    }


}
