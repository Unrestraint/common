package cloud.zmh.common.autoconfigure.chain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 责任链配置对象
 * @author Unrestraint
 */
@Data
@ConfigurationProperties(prefix = "chain")
public class ChainProperties {
    private List<ChainConfig> chains;
}
