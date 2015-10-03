package stlb;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by demos_000 on 03.10.2015.
 */
@Component
public class SheduledTasks {

    public SheduledTasks() {
        System.out.println("SheduledTasks inited");
    }

    @Scheduled(fixedRate=1000)
    public void task(){
        System.out.println("Вьебал говна по плану");
    }
}
