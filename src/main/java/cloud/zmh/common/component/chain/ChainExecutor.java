package cloud.zmh.common.component.chain;

import cloud.zmh.common.util.SpringUtil;

/**
 *  责任链执行器，从Spring 容器中获取bean执行
 * @author Unrestraint
 */
public class ChainExecutor<T> implements Runnable {
    private T context;
    private Handle<T> handle;

    public ChainExecutor(String chainId, T context) {
        this.context = context;
        this.handle = (Handle<T>) SpringUtil.getBean(chainId);
    }

    @Override
    public void run() {
        handle.handle(context);
    }
}
