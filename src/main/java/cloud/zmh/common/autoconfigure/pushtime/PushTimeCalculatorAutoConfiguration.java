package cloud.zmh.common.autoconfigure.pushtime;

import cloud.zmh.common.autoconfigure.condition.ConditionalOnPrefix;
import cloud.zmh.common.component.pushtime.PushTimeCalculator;
import cloud.zmh.common.component.pushtime.PushTimeCalculatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  推送时间计算组件自动配置类
 * @author Unrestraint
 */
@Slf4j
@Configuration
@ConditionalOnPrefix(prefix = "pushtime")
@EnableConfigurationProperties(PushTimeProperties.class)
public class PushTimeCalculatorAutoConfiguration {

    @Bean
    public PushTimeCalculator getPushTimeCalculator(PushTimeProperties pushTimeProperties) {
        return  new PushTimeCalculatorImpl(pushTimeProperties.getTemplates());
    }
}
