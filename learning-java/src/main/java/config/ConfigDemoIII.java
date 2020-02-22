package config;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.typesafe.config.*;

public class ConfigDemoIII {

    public static void main(String[] args) {
        System.out.println(getRealtimeFeatureAsMap());
    }

    public static Map<String, RealtimeFeature> getRealtimeFeatureAsMap() {
        Config conf = ConfigFactory.parseResources("realtime-feature.conf");
        List<? extends Config> lc = conf.getConfigList("realtime-feature");
        Map<String, RealtimeFeature> m = new HashMap<>();
        for (Config c : lc) {
            Long id = c.getLong("id");
            String mark = c.getString("mark");
            String name = c.getString("name");
            String defalutValue = c.getString("default");
            String type = c.getString("type");
            RealtimeFeature rf = new RealtimeFeature(id, mark, name, defalutValue, type);
            m.put(name, rf);
        }
        return m;
    }

}

class RealtimeFeature {

    private Long id;
    private String mark;
    private String name;
    private String defaultValue;
    private String type;

    public RealtimeFeature(Long id, String mark, String name, String defaultValue, String type) {
        this.id = id;
        this.mark = mark;
        this.name = name;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
