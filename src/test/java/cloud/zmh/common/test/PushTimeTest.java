package cloud.zmh.common.test;

import cloud.zmh.common.component.pushtime.PushTimeCalculator;
import cloud.zmh.common.component.pushtime.PushTimeCalculatorImpl;
import cloud.zmh.common.component.pushtime.PushTimeConfigItem;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PushTimeTest {

    @Test
    public void pushTimeTest() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, List<PushTimeConfigItem>> map = new HashMap<>();
        List<PushTimeConfigItem> list = new ArrayList<>();

        PushTimeConfigItem item0 = new PushTimeConfigItem();
        item0.setDeadlineCron("0 30 9 * * 1 ");
        item0.setTimeRange(63*60*60);
        item0.setPushTimeCron("0 30 10 * * 1 ");
        list.add(item0);

        PushTimeConfigItem item1 = new PushTimeConfigItem();
        item1.setDeadlineCron("0 30 9 * * ? ");
        item1.setTimeRange(15*60*60);
        item1.setPushTimeCron("0 30 10 * * ? ");
        list.add(item1);

        PushTimeConfigItem item2 = new PushTimeConfigItem();
        item2.setDeadlineCron("0 0 14 * * ? ");
        item2.setTimeRange((long) (4.5 * 60 * 60));
        item2.setPushTimeNo(1);
        item2.setPushTimeCron("0 0 15 * * ? ");
        list.add(item2);

        PushTimeConfigItem item3 = new PushTimeConfigItem();
        item3.setDeadlineCron("0 30 18 * * ? ");
        item3.setTimeRange((long) (4.5*60*60));
        item3.setPushTimeCron("0 30 19 * * ? ");
        list.add(item3);
        map.put("shopee", list);

        PushTimeCalculator calculator = new PushTimeCalculatorImpl(map);
        Date date = calculator.getPushTime("shopee", dateFormat.parse("2019-06-21 19:16:20"));
        System.out.println(dateFormat.format(date));
    }
}
