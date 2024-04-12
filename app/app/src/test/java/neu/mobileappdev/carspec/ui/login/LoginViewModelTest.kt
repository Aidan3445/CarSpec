package neu.mobileappdev.carspec.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import neu.mobileappdev.carspec.api.ApiService
import neu.mobileappdev.carspec.api.LoginRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    // Required to test LiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: LoginRepository
    private lateinit var observer: Observer<String?>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        repository = FakeRepo()
        viewModel = LoginViewModel(repository)
        observer = Observer {  }
        viewModel.message.observeForever(observer)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun testLoginSuccess() = runTest {
        // given
        val username = "admin"
        val password = "password"

        // preconditions
        assertEquals(null, viewModel.message.value)
        assertEquals(null, viewModel.loginSuccess.value)

        // when
        viewModel.tryLogin(username, password)

        // then
        viewModel.loginSuccess.observeForever {
            assert(true)
        }
        assertEquals(null, viewModel.message.value)
    }

    @Test
    fun testCorrectCredentials() = runTest {
        // randomize partially incorrect credentials
        val rand = (0..2).random()
        // given
        val username = "admin" + if (rand == 0 || rand == 2) "NOT" else ""
        val password = "password" + if (rand == 1 || rand == 2) "NOT" else ""

        // preconditions
        assertEquals(null, viewModel.message.value)
        assertEquals(null, viewModel.loginSuccess.value)

        // when
        viewModel.tryLogin(username, password)

        // then
        viewModel.loginSuccess.observeForever {
            assert(false)
        }
        assertEquals("Invalid username or password\nPlease Try Again", viewModel.message.value)
    }

    @Test
    fun testFetchException() = runTest {
        // given
        val username = "throw"
        val password = "exception"

        // preconditions
        assertEquals(null, viewModel.message.value)
        assertEquals(null, viewModel.loginSuccess.value)

        // when
        viewModel.tryLogin(username, password)

        // then
        viewModel.loginSuccess.observeForever {
            assert(false)
        }
        assertEquals("fetch exception", viewModel.message.value)
    }

    @Test
    fun testGetHint() = runTest {
        // preconditions
        assertEquals(null, viewModel.message.value)

        // when
        viewModel.getHint()

        // then
        assertEquals("password hint", viewModel.message.value)
    }

    @Test
    fun testClearErrorMessage() = runTest {
        // given
        viewModel.message.value = "error message"

        // preconditions
        assertEquals("error message", viewModel.message.value)

        // when
        viewModel.clearErrorMessage()

        // then
        assertEquals(null, viewModel.message.value)
    }

}

class FakeRepo() : LoginRepository() {
    override suspend fun login(username: String, password: String): Boolean {
        // simulate fetch exception for testing
        if (username == "throw" && password == "exception") {
            throw ApiService.FetchException("fetch exception")
        }

        return username == "admin" && password == "password"
    }

    override suspend fun getHint(): String {
        return "password hint"
    }
}