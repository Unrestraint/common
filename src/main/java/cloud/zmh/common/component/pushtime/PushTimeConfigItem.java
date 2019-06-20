package cloud.zmh.common.component.pushtime;

import lombok.Data;

/**
 *  推送时间配置，
 *  deadlineCron 截止日期 cron 表达式
 *  timeRange 时间范围, 开始日期向前推timeRange时间,单位秒
 *  pushTimeNo 第几个推送时间，默认为1
 *  pushTime 推送时间
 * @author Unrestraint
 */
@Data
public class PushTimeConfigItem{
    private String deadlineCron;
    private long timeRange;
    private int pushTimeNo;
    private String pushTimeCron;
}
