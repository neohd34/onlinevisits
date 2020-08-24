package me.chat.onlinevisits.config

import com.hazelcast.config.Config
import com.hazelcast.config.EvictionPolicy
import com.hazelcast.config.GroupConfig
import com.hazelcast.config.JoinConfig
import com.hazelcast.config.MapConfig
import com.hazelcast.config.MaxSizeConfig
import com.hazelcast.config.MulticastConfig
import com.hazelcast.config.NetworkConfig
import com.hazelcast.config.TcpIpConfig
import com.hazelcast.config.UserCodeDeploymentConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.IMap
import com.hazelcast.spi.properties.GroupProperty
import me.chat.common.data.cache.CachedVisitor
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
class HazelcastConfig : DisposableBean {

    @Value("\${hz.members}")
    private lateinit var members: List<String>

    @Value("\${spring.application.name}")
    private lateinit var applicationName: String

    @Bean
    fun hazelCastConfig(): Config {
        val uc = UserCodeDeploymentConfig()
        uc.isEnabled = true
        return Config()
            .setUserCodeDeploymentConfig(uc)
            .setInstanceName("$applicationName-instance")
            .setGroupConfig(GroupConfig("onlinevisitors-group"))
            .setProperty(GroupProperty.SHUTDOWNHOOK_ENABLED.name, "false")
            .setNetworkConfig(
                NetworkConfig().setJoin(
                    JoinConfig()
                        .setMulticastConfig(MulticastConfig().setEnabled(false))
                        .setTcpIpConfig(TcpIpConfig().setEnabled(true).setMembers(members))
                )
            )
            .addMapConfig(
                MapConfig()
                    .setName("visitors")
                    .setMaxSizeConfig(MaxSizeConfig(100000, MaxSizeConfig.MaxSizePolicy.PER_NODE))
                    .setTimeToLiveSeconds(60 * 2)
                    .setEvictionPolicy(EvictionPolicy.LRU)
            )
    }

    @Bean
    fun hz(): HazelcastInstance {
        return Hazelcast.getOrCreateHazelcastInstance(hazelCastConfig())
    }

    @Bean
    fun visitors(): IMap<UUID, CachedVisitor> {
        return hz().getMap("visitors")
    }

    override fun destroy() {
        Hazelcast.getOrCreateHazelcastInstance(hazelCastConfig()).shutdown()
    }
}