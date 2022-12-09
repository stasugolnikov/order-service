package com.itmo.microservices.order

import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
class BaseIntegrationTest {
    companion object {
        @Container
        private val mongoDBContainer = GenericContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts( 27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "root")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "rootpassword")

        @JvmStatic
        @BeforeAll
        fun startMongoDB() {
            mongoDBContainer.start()
            System.setProperty("spring.data.mongodb.port", mongoDBContainer.getMappedPort(27017).toString())
        }
    }

}