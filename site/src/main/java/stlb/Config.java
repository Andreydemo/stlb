package stlb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by demos_000 on 03.10.2015.
 */
@Configuration
@EnableAspectJAutoProxy
public class Config {
    public Config() {
        System.out.println("Configs inited");
    }
}
