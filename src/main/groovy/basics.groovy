import redis.clients.jedis.Jedis
import redis.clients.jedis.Transaction

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

Jedis jedis = new Jedis('localhost')

jedis.flushAll()

println '--- increments ---'
println jedis.incr('counter')
println jedis.decr('counter')

println '--- expiration ---'
jedis.set "session-1", "data"
jedis.expire "session-1", 2
println jedis.get("session-1")
sleep(3000)
println jedis.get("session-1")

println '--- maps ---'
jedis.hset "members", "1", "mathieu"
jedis.hset "members", "2", "david"
println jedis.hgetAll("members")

println '--- strings ---'
jedis.set "welcome", "hello"
println jedis.get("welcome")
jedis.append 'welcome', ' world !'
println jedis.get("welcome")

println '--- lists ---'
jedis.lpush "list", "2"
jedis.lpush "list", "1"
jedis.rpush "list", "3"
println jedis.lrange("list", 0, Long.MAX_VALUE)




















