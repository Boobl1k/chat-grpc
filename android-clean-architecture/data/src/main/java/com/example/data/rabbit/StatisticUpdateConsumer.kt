package com.example.data.rabbit

import com.example.domain.dto.StatisticData
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class StatisticUpdateConsumer {
    suspend fun subscribe(onStatisticUpdate: (statistic: StatisticData) -> Unit) {
        withContext(Dispatchers.Default) {
            while (true) {
                try {
                    val factory = ConnectionFactory()
                    factory.host = "10.0.2.2"
                    factory.port = 5672
                    factory.username = "guest"
                    factory.password = "guest"
                    val connection = factory.newConnection()
                    val channel = connection.createChannel()
                    channel.basicQos(1)
                    channel.queueDeclare("statistics_queue", true, false, false, mapOf())
                    channel.basicConsume("statistics_queue", true, "android",
                        object : DefaultConsumer(channel) {
                            override fun handleDelivery(
                                consumerTag: String,
                                envelope: Envelope,
                                properties: AMQP.BasicProperties,
                                body: ByteArray
                            ) {
                                val routingKey = envelope.routingKey
                                val contentType = properties.contentType
                                val deliveryTag = envelope.deliveryTag

                                val jsonObject = JSONObject(String(body, StandardCharsets.UTF_8))
                                println("consumed: [$jsonObject]")
                                val statisticData = StatisticData(
                                    jsonObject.getString("BookId"),
                                    jsonObject.getInt("ReadCount")
                                )
                                onStatisticUpdate(statisticData)

                                channel.basicAck(deliveryTag, false)
                            }
                        })
                } catch (e1: Exception) {
                    println("Connection broken: " + e1.message)
                    e1.printStackTrace()
                    try {
                        Thread.sleep(5000) //sleep and then try again
                    } catch (_: InterruptedException) {
                    }
                }
                Thread.sleep(1000)
            }
        }
    }
}
