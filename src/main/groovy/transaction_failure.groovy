import redis.clients.jedis.Jedis
import redis.clients.jedis.Transaction

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

Jedis jedis = new Jedis('localhost')
jedis.flushAll()

jedis.watch "count", "test"

println '---before---'
println jedis.get("test")
println jedis.get("count")

println '---tx---'
println (jedis.multi().with {Transaction tx ->
    tx.set "test", "transaction"
    
    // simulate a new Redis connection from another thread which modifies some keys impacted by the transaction
    new Jedis('localhost').with {
        it.set "test", "boum!"
        it.incr "count"
    }

    // our transaction continues and commits...
    tx.incr "count"
    return tx.exec()
})

println '---after---'
println jedis.get("test")
println jedis.get("count")
