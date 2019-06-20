package cloud.zmh.common.autoconfigure.spring;

import cloud.zmh.common.util.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *  Spring工具类
 * @author Unrestraint
 */
@Configuration
@Import(SpringUtil.class)
public class SpringUtilAutoConfiguration {

}
