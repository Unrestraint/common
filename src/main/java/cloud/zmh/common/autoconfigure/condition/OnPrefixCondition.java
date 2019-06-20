package cloud.zmh.common.autoconfigure.condition;


import org.springframework.boot.context.properties.source.*;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 *  当注解ConditionalOnPrefix的 prefix在配置文件中存在时，进行加载
 * @author Unrestraint
 */
public class OnPrefixCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Map<String, Object> attrMap =  annotatedTypeMetadata.getAnnotationAttributes(ConditionalOnPrefix.class.getName());
        String prefix = getAttr(attrMap, "prefix");
        if (prefix.isEmpty()) {
            return false;
        }

        ConfigurationPropertyName name = ConfigurationPropertyName.of(prefix);
        for (ConfigurationPropertySource propertySource : ConfigurationPropertySources.get(conditionContext.getEnvironment())) {
            ConfigurationPropertyState state = propertySource.containsDescendantOf(name);
            ConfigurationProperty property = propertySource.getConfigurationProperty(name);
            if (state == ConfigurationPropertyState.PRESENT || property != null) {
                return true;
            }
        }
        return false;
    }

    private String getAttr(Map<String, Object> attrMap, String key) {
        Object attr = attrMap.get(key);
        return  attr == null ? "" : attr.toString().trim();
    }
}
