package neu.mobileappdev.carspec.api

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class FakeApiService() : ApiService {
    private val cars = setOf(
        Car(1, "Car1", "Make1", 2021),
        Car(2, "Car2", "Make2", 2022),
        Car(3, "Car3", "Make3", 2023),
        Car(4, "Car4", "Make4", 2024),
        Car(5, "Car5", "Make5", 2025),
    )

    private val specs = setOf(
        Specs(1, "Engine1", "1 mpg",
            Dimension(1.0, 1.1, 1.11), 111, 1.1),
        Specs(2, "Engine2", "2 mpg",
            Dimension(2.0, 2.2, 2.22), 222, 2.2),
        Specs(3, "Engine3", "3 mpg",
            Dimension(3.0, 3.3, 3.33), 333, 3.3),
        Specs(4, "Engine4", "4 mpg",
            Dimension(4.0, 4.4, 4.44), 444, 4.4),
        Specs(5, "Engine5", "5 mpg",
            Dimension(5.0, 5.5, 5.55), 555, 5.5),
    )

    override suspend fun getCars(name: String?, make: String?, year: Int?): Response<Set<Car>> {
        val queried = cars.filter {
            (name.isNullOrEmpty() || it.name == name) &&
                    (make.isNullOrEmpty() || it.make == make) &&
                    (year == null || it.year == year)
        }.toSet()

        return Response.success(queried)
    }

    override suspend fun getCar(id: Int): Response<Car> {
        val car = cars.find { it.id == id }
            ?: return Response.error(
                404,
                ResponseBody.create(
                    MediaType.parse("text/plain"),
                    "{error: Car not found}"))

        return Response.success(car)
    }

    override suspend fun getSpecs(id: Int): Response<Specs> {
        val spec = specs.find { it.id == id }
            ?: return Response.error(
                404,
                ResponseBody.create(
                    MediaType.parse("text/plain"),
                    "{error: Car not found}"))

        return Response.success(spec)
    }

    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        if (username == "admin" && password == "password") {
            return Response.success(LoginResponse(true))
        }

        return Response.error(
            401,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Invalid username or password}"))
    }

    override suspend fun getHint(): Response<LoginResponse> {
        return Response.success(LoginResponse(false, "hint"))
    }
}

class FailApiService() : ApiService {
    override suspend fun getCars(name: String?, make: String?, year: Int?): Response<Set<Car>> {
        return Response.error(
            500,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Server Error}"))
    }

    override suspend fun getCar(id: Int): Response<Car> {
        return Response.error(
            500,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Server Error}"))
    }

    override suspend fun getSpecs(id: Int): Response<Specs> {
        return Response.error(
            500,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Server Error}"))
    }

    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        return Response.error(
            500,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Server Error}"))
    }

    override suspend fun getHint(): Response<LoginResponse> {
        return Response.error(
            500,
            ResponseBody.create(
                MediaType.parse("text/plain"),
                "{error: Server Error}"))
    }
}