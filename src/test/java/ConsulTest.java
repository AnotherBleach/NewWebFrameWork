//import com.orbitz.consul.AgentClient;
//import com.orbitz.consul.Consul;
//import com.orbitz.consul.model.agent.ImmutableRegistration;
//import com.orbitz.consul.model.agent.Registration;
//import org.junit.Test;
//
//import java.util.Collections;
//
//public class ConsulTest {
//    @Test
//    public void add() {
//        Consul consul = Consul.builder().build();
//        AgentClient agentClient = consul.agentClient();
//        Registration service = ImmutableRegistration.builder()
//                .id("3")
//                .name("org.service.add")
//                .address("localhost")
//                .port(9999)
//                .tags(Collections.singletonList("tag1"))
//                .meta(Collections.singletonMap("version", "1.0"))
//                .build();
//
//        agentClient.register(service);
//  //  agentClient.deregister("2");
//
//
//    }
//}
