import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

private const val PRICE_PER_CUPCAKE = 2.00

class ViewModelTests {
    @get:Rule
    // Specify that LiveData objects should not call the main thread.
    // We need to provide a specific test rule any time we are testing a LiveData object.
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun quantity_six_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.setQuantity(6)
        assertEquals(6, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        viewModel.setQuantity(12)
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        assertEquals(formatter.format(PRICE_PER_CUPCAKE * 12), viewModel.price.value)
    }

    @Test
    fun price_six_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        viewModel.setQuantity(6)
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        assertEquals(formatter.format(PRICE_PER_CUPCAKE * 6), viewModel.price.value)
    }

}