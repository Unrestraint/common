package cloud.zmh.common.component.chain;

/**
 *  执行链处理的核心接口，每个Handle仅仅处理一件事情，简单性
 *  handle 返回false 中断执行
 * @author Unrestraint
 */
public interface Handle<T> {
    /**
     *  执行方法
     */
    boolean handle(T context);
    /**
     *  该链是否触发执行，默认执行
     */
    default boolean excCondition(T context) {
        return true;
    }
}
