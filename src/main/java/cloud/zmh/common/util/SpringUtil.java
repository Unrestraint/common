package cloud.zmh.common.util;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *  spring 工具类
 *  部分函数需要spring 容器加载完成后使用
 * @author Unrestraint
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


    /**
     *  当容器加载完毕后，从配置文件中读取配置对象
     * @throws NullPointerException  该bean还未加载
     */
    public static <T> T getConfigurationProperties(String prefix, Class<T> clazz) {
        return getConfigurationProperties(getApplicationContext().getEnvironment(), prefix, clazz);
    }
    /**
     *  获取配置对象，如果无法生成对象，则抛出异常
     *  多次调用非同一对象
     * @return
     */
    public static <T> T getConfigurationProperties(Environment environment , String prefix, Class<T> clazz) {
        Binder binder = new Binder(ConfigurationPropertySources.get(environment));
        BindResult<T> bindResult = binder.bind(prefix, clazz);
        return bindResult.orElse(null);
    }

    /**
     *  根据带有ConfigurationProperties注解的配置对象获取配置对象
     */
    public static <T> T getConfigurationProperties(Environment environment, Class<T> clazz){
        ConfigurationProperties annotation = clazz.getAnnotation(ConfigurationProperties.class);
        Assert.notNull(annotation, "配置对象必须有 ConfigurationProperties 注解");
        String prefix =  annotation.prefix();
        return getConfigurationProperties(environment, prefix, clazz);
    }
}
