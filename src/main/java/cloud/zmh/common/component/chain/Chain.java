package cloud.zmh.common.component.chain;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *  对执行链进行执行
 * @author Unrestraint
 */
@Slf4j
@Setter
public class Chain<T>  implements Handle<T> {
    protected List<Handle<T>> handles;
    protected String id;
    @Override
    public boolean handle(T context) {
        if (excCondition(context)) {
            return this.execute(context);
        }
        return true;
    }
    public boolean execute(T context){

        log.debug("执行Chain {} 开始", id);
        long chainStart = System.currentTimeMillis();

        boolean result = false;
        for (Handle<T> handle : handles) {
            if (handle.excCondition(context)){
                result = handle.handle(context);
                if (!result) {
                    break;
                }
            }
        }

        log.debug("执行Chain {} 耗时 {}", id, System.currentTimeMillis() - chainStart);

        return result;
    }
}
