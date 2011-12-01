import redis.clients.jedis.Jedis
import redis.clients.jedis.Pipeline
import redis.clients.jedis.Response

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

Jedis jedis = new Jedis('localhost')
jedis.flushAll()

// create a pipeline
Pipeline p = jedis.pipelined()

p.set "test", "pipelining"
Response<Long> futureCount = p.incr "count"
Response<String> futureFoo = p.get "test"

p.sync()

println futureCount.get()
println futureFoo.get()
