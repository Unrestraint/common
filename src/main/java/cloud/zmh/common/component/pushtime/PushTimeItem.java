package cloud.zmh.common.component.pushtime;

import lombok.Data;
import org.springframework.scheduling.support.CronSequenceGenerator;

@Data
class PushTimeItem {
    private CronSequenceGenerator deadlineCronGenerator;
    private long timeRange;
    private int pushTimeNo;
    private CronSequenceGenerator pushTimeCronGenerator;
}
