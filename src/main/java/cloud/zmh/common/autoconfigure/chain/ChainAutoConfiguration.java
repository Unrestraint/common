package cloud.zmh.common.autoconfigure.chain;

import cloud.zmh.common.autoconfigure.condition.ConditionalOnPrefix;
import cloud.zmh.common.component.chain.Chain;
import cloud.zmh.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *  责任链自动装配
 * @author Unrestraint
 */
@Slf4j
@Configuration
@ConditionalOnPrefix(prefix = "chain")
@EnableConfigurationProperties(ChainProperties.class)
public class ChainAutoConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String DEFAULT_CHAIN_CLASS = Chain.class.getName();

    private ChainProperties chainProperties;

    @Override
    public void setEnvironment(Environment environment) {
        this.chainProperties = SpringUtil.getConfigurationProperties(environment, ChainProperties.class);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (chainProperties == null || chainProperties.getChains() == null) {
            // 未进行配置
            return;
        }
        for (ChainConfig chainConfig : chainProperties.getChains()) {
            if (! CollectionUtils.isEmpty(chainConfig.getHandles())) {
                String clazzName = StringUtils.isEmpty(chainConfig.getClazz()) ? DEFAULT_CHAIN_CLASS : chainConfig.getClazz();
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazzName);
                List<BeanReference> handleBeanReferences = new ManagedList<>(chainConfig.getHandles().size());
                for (String handle : chainConfig.getHandles()) {
                    handleBeanReferences.add(new RuntimeBeanReference(handle));
                }
                beanDefinitionBuilder.addPropertyValue("handles", handleBeanReferences);
                beanDefinitionBuilder.addPropertyValue("id", chainConfig.getId());
                beanDefinitionRegistry.registerBeanDefinition(chainConfig.getId(), beanDefinitionBuilder.getBeanDefinition());
            }
        }
    }
}
