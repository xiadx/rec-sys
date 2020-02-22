package config;

import com.typesafe.config.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConfigDemoI {

    public static void main(String[] args) {
        System.setProperty("simple-lib.whatever", "This value comes from a system property");
        Config conf = ConfigFactory.load();
        System.out.println(conf.getClass());
        System.out.println("--------------------------------------------------");
        Set<Map.Entry<String, ConfigValue>> se = conf.entrySet();
        for (Iterator<Map.Entry<String, ConfigValue>> iter = se.iterator(); iter.hasNext(); ) {
            Map.Entry<String, ConfigValue> t = iter.next();
            System.out.println(t.getKey() + "->" + t.getValue());
        }
        System.out.println("--------------------------------------------------");
        System.out.println(conf.getString("simple-lib.whatever"));
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("{\"name\":\"xiadingxin\",\"age\":18}");
        System.out.println(conf.getString("name"));
        System.out.println(conf.getInt("age"));
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("{name:xiadingxin,age:10}");
        System.out.println(conf.getString("name"));
        System.out.println(conf.getInt("age"));
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("name=xiadingxin,age=10");
        System.out.println(conf.getString("name"));
        System.out.println(conf.getInt("age"));
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("user{name=xiadingxin,age=18}");
        System.out.println(conf.getObject("user").getClass());
        System.out.println(conf.getObject("user").toConfig().getString("name"));
        System.out.println(conf.getObject("user").toConfig().getString("age"));
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("user=[{name=xiadingxin,age=18},{name=wanglei,age=20}]");
        System.out.println(conf.getObjectList("user").getClass());
        java.util.List<? extends ConfigObject> us = conf.getObjectList("user");
        for (ConfigObject co : us) {
            System.out.println("name->" + co.toConfig().getString("name") + ",age->" + co.toConfig().getInt("age"));
        }
        System.out.println("--------------------------------------------------");
        conf = ConfigFactory.parseString("user=[xiadingxin,wanglei]");
        java.util.List<String> uu = conf.getStringList("user");
        for (String u : uu) {
            System.out.println(u);
        }
    }

}
