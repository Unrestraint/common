package cloud.zmh.common.autoconfigure.chain;

import lombok.Data;

import java.util.List;

/**
 * id 责任链 bean 在Spring 容器的 id
 * clazz 责任链bean 的装配类， 默认为cloud.zmh.common.component.chain.Chain, 设置的clazz必须继承该类
 * handles 责任链节点
 * @author Unrestraint
 */
@Data
public class ChainConfig {
    private String id;
    private String clazz;
    private List<String> handles;
}
