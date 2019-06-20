package cloud.zmh.common.component.pushtime;

import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 *  当时间范围在 （截止日期-时间范围， 截止日期） 内时，
 *  计算pushTimeNo次推送时间，取最后一次推送时间
 *  如果未获取到推送时间，返回null
 * @author Unrestraint
 */
public class PushTimeCalculatorImpl implements PushTimeCalculator {

    private Map<String, List<PushTimeItem>> config = Collections.emptyMap();

    public PushTimeCalculatorImpl(Map<String, List<PushTimeConfigItem>> configListMap) {
        this.initItems(configListMap);
    }

    @Override
    public Date getPushTime(String configKey, Date date) {
        List<PushTimeItem> list = config.get(configKey);
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(configKey + "无配置信息！");
        }
        return getPushTime(list,date);
    }

    /**
     *  依次匹配配置的时间范围列表， 当能够获取到推送时间时会停止
     * @return
     */
    private Date getPushTime(List<PushTimeItem> list, Date date) {
        Date pushTime = null;
        for (PushTimeItem item : list) {
            pushTime = getPushTime(item, date);
            if (pushTime != null) {
                break;
            }
        }
        return pushTime;
    }

    /**
     *  当date 在该配置时间范围内，获取推送时间
     * @param item
     * @param date
     * @return
     */
    private Date getPushTime(PushTimeItem item, Date date) {
        Date pushTime = null;
        Date endTime = item.getDeadlineCronGenerator().next(date);
        long beginTime = endTime.getTime() - item.getTimeRange();
        if (beginTime <= date.getTime()) {
            pushTime = endTime;
            for (int i=0; i < item.getPushTimeNo(); i++) {
                pushTime = item.getPushTimeCronGenerator().next(pushTime);
            }
        }
        return pushTime;
    }

    private void initItems(Map<String, List<PushTimeConfigItem>> configListMap) {

        if (! CollectionUtils.isEmpty(configListMap)) {

            config = new HashMap<>(configListMap.size());
            configListMap.forEach((key,configList)->{

                if (! CollectionUtils.isEmpty(configList)) {
                    List<PushTimeItem> items = new ArrayList<>(configList.size());
                    for (PushTimeConfigItem configItem : configList) {
                        PushTimeItem item = new PushTimeItem();
                        item.setDeadlineCronGenerator(new CronSequenceGenerator(configItem.getDeadlineCron()));
                        item.setPushTimeCronGenerator(new CronSequenceGenerator(configItem.getPushTimeCron()));

                        if (configItem.getTimeRange() < 1) {
                            throw new IllegalArgumentException("时间范围不能小于 1！");
                        }
                        // 将秒转化为ms
                        item.setTimeRange(configItem.getTimeRange() * 1000);

                        Integer pushTimeNo = configItem.getPushTimeNo();
                        item.setPushTimeNo(pushTimeNo < 1 ? 1 : pushTimeNo);

                        items.add(item);
                    }
                    config.put(key, items);
                }
            });
        }
    }
}
