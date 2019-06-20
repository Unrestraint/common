package cloud.zmh.common.autoconfigure.pushtime;

import cloud.zmh.common.component.pushtime.PushTimeConfigItem;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties("pushtime")
public class PushTimeProperties {
    private Map<String,List<PushTimeConfigItem>> templates;
}
