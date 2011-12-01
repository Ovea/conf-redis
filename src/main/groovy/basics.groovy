import redis.clients.jedis.Jedis
import redis.clients.jedis.Transaction

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

Jedis jedis = new Jedis('localhost')
jedis.flushAll()

