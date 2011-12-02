import redis.clients.jedis.JedisPool
import org.apache.commons.pool.impl.GenericObjectPool
import redis.clients.jedis.Jedis

@Grab('redis.clients:jedis:2.0.0')
@Grab('commons-pool:commons-pool:1.5.6')

// pool definition
JedisPool pool = new JedisPool(new GenericObjectPool.Config(
    minIdle: 5,
    maxActive: 100,
    testOnBorrow: true,
    testOnReturn: true,
    testWhileIdle: true
), '127.0.0.1', 6379)

// borrowing a resource (redis connection)
Jedis jedis = pool.getResource()
try {
    // execute some redis commands
    jedis.append 'key', 'a '
    jedis.append 'key', 'value'
    assert 'a value' == jedis.get('key')
} finally {
    // return the connection
    pool.returnResource jedis
}

// pool cleanup
pool.destroy();
