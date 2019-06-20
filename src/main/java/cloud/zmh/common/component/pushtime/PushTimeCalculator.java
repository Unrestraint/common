package cloud.zmh.common.component.pushtime;

import java.util.Date;

/**
 *  根据config配置计算推送时间
 *  <b> 如果要求计算出的推送时间不能为null, 则时间配置一定要能够覆盖所有时间 </b>
 * @author Unrestraint
 */
public interface PushTimeCalculator {
    /**
     *  当 时间在 （截止日期-时间范围， 截止日期）内时，根据 pushTimeCron 获取第 pushTimeNo 次推送时间
     * @param config 配置key
     * @param date 需要计算的时间
     * @return null 如果未获取到推送时间，返回null
     */
    Date getPushTime(String config, Date date);
}
