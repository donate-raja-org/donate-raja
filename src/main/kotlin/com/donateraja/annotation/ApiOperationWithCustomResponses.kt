import com.donateraja.common.exception.ServiceError
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.core.annotation.AliasFor
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Operation
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "200",
            description = "Success",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Any::class),
                    examples = [
                        ExampleObject(
                            value = """{"message": "Operation successful", "data": {"userId": 123}}"""
                        )
                    ]
                )
            ]
        ),
        ApiResponse(
            responseCode = "201",
            description = "Created",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Any::class),
                    examples = [ExampleObject(value = """{"message": "User created successfully", "userId": 123}""")]
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = ServiceError::class))]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = ServiceError::class))]
        ),
        ApiResponse(
            responseCode = "403",
            description = "Forbidden",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = ServiceError::class))]
        ),
        ApiResponse(
            responseCode = "415",
            description = "Unsupported Media Type",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = ServiceError::class))]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = ServiceError::class))]
        )
    ]
)
annotation class ApiOperationWithCustomResponses(
    @get:AliasFor(annotation = Operation::class, attribute = "summary")
    val summary: String,

    @get:AliasFor(annotation = Operation::class, attribute = "description")
    val description: String,

    val successSchema: KClass<out Any> = Any::class,
    val responseExamples: Array<ExampleObject> = arrayOf()
)
