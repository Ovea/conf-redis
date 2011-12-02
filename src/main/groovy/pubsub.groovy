import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

Jedis jedis = new Jedis('localhost')
long id = jedis.incr "user.id"
long msg;

println "I am user ${id}"

JedisPubSub subscriber = [
    onMessage: {String channel, String message ->
        println message
    },
    onPMessage: {String pattern, String channel, String message ->},
    onSubscribe: {String channel, int subscribedChannels ->},
    onUnsubscribe: {String channel, int subscribedChannels ->},
    onPUnsubscribe: {String pattern, int subscribedChannels ->},
    onPSubscribe: {String pattern, int subscribedChannels ->}
] as JedisPubSub

Thread.start {
    new Jedis('localhost').subscribe(subscriber, 'chatroom.public')
}

while (!Thread.currentThread().interrupted) {
    sleep 5000
    jedis.publish "chatroom.public", "[user-${id}] message ${msg++}"
}

//subscriber.unsubscribe()
