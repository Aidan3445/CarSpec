package neu.mobileappdev.carspec.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarViewModel(private val carID: Int) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Car Fragment"
    }
    val text: LiveData<String> = _text
}