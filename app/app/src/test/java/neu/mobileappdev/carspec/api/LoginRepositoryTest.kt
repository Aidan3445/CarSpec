package neu.mobileappdev.carspec.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class LoginRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: LoginRepository
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = LoginRepository(apiService)
    }

    @Test
    fun loginSuccess() = runTest {
        // given
        val username = "admin"
        val password = "password"

        // when
        val result = repository.login(username, password)

        // then
        assertTrue(result)
    }

    @Test
    fun loginFailureBadCredentials() = runTest {
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

        try {
            // when
            repository.login(username, password)
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Invalid username or password", e.message)
        }
    }

    @Test
    fun loginFailureServerError() = runTest {
        // given
        apiService = FailApiService()
        repository = LoginRepository(apiService)

        try {
            // when
            repository.login("admin", "password")
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Server Error", e.message)
        }
    }

    @Test
    fun getHint() = runTest {
        // when
        val hint = repository.getHint()

        // then
        assertEquals("hint", hint)
    }

    @Test
    fun getHintFailure() = runTest {
        // given
        apiService = FailApiService()
        repository = LoginRepository(apiService)

        try {
            // when
            repository.getHint()
        } catch (e: ApiService.FetchException) {
            // then
            assertEquals("Unknown error while getting hint", e.message)
        }
    }
}