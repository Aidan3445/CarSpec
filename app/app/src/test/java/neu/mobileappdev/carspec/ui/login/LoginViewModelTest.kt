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
import java.util.UUID

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
        observer = Observer { }
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
        val username = if (rand == 0 || rand == 2) {
            UUID.randomUUID().toString()
        } else {
            "admin"
        }
        val password = if (rand == 1 || rand == 2) {
            UUID.randomUUID().toString()
        } else {
            "password"
        }

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
    fun testCarsFetchException() = runTest {
        // given
        viewModel = LoginViewModel(FetchExceptionRepo())

        // preconditions
        assertEquals(null, viewModel.message.value)
        assertEquals(null, viewModel.loginSuccess.value)

        // when
        viewModel.tryLogin(UUID.randomUUID().toString(), UUID.randomUUID().toString())

        // then
        viewModel.loginSuccess.observeForever {
            assert(false)
        }
        assertEquals("fetch exception", viewModel.message.value)
    }

    @Test
    fun testCarOtherException() = runTest {
        // given
        viewModel = LoginViewModel(OtherExceptionRepo())

        // preconditions
        assertEquals(null, viewModel.message.value)
        assertEquals(null, viewModel.loginSuccess.value)

        // when
        viewModel.tryLogin(UUID.randomUUID().toString(), UUID.randomUUID().toString())

        // then
        viewModel.loginSuccess.observeForever {
            assert(false)
        }
        assertEquals(
            "An error occurred while trying to login\nPlease Try Again",
            viewModel.message.value
        )
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
    fun testClearMessage() = runTest {
        // given
        viewModel.message.value = "message"

        // preconditions
        assertEquals("message", viewModel.message.value)

        // when
        viewModel.clearErrorMessage()

        // then
        assertEquals(null, viewModel.message.value)
    }
}

open class FakeRepo : LoginRepository() {
    override suspend fun login(username: String, password: String): Boolean {
        return username == "admin" && password == "password"
    }

    override suspend fun getHint(): String {
        return "password hint"
    }
}

class FetchExceptionRepo : FakeRepo() {
    override suspend fun login(username: String, password: String): Boolean {
        throw ApiService.FetchException("fetch exception")
    }
}

class OtherExceptionRepo : FakeRepo() {
    override suspend fun login(username: String, password: String): Boolean {
        throw Exception("other exception")
    }
}