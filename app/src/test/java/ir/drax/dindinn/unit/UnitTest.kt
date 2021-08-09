package ir.drax.dindinn.unit

import com.interview.blocket.util.TextLoader.getFormattedDate
import junit.framework.Assert.assertEquals
import org.junit.Test

class UnitTest {

    @Test
    fun dateFormattingTest() {
        val unformattedDate = "2018-04-13T08:34:33Z"
        val formattedDate = "2018-04-13"
        assertEquals(formattedDate, getFormattedDate(unformattedDate))
    }
}