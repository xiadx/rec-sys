package config;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;

import com.typesafe.config.*;

public class ConfigDemoII {

    public static void main(String[] args) {
        Config dbConf = ConfigFactory.load("db.conf");
        Map<String, Object> mc = dbConf.getConfig("mysql").root().unwrapped();
        Set<String> ks = mc.keySet();
        for (String k : ks) {
            System.out.println(k + "=" + mc.get(k));
        }
        System.out.println(dbConf.getString("mysql.driver"));
        System.out.println(dbConf.getString("mysql.url"));
        System.out.println(dbConf.getString("mysql.user"));
        System.out.println(dbConf.getString("mysql.pwd"));
        System.out.println("----------------------------------------");
        Set<Map.Entry<String, ConfigValue>> se = dbConf.entrySet();
        for (Iterator<Map.Entry<String, ConfigValue>> iter = se.iterator(); iter.hasNext(); ) {
            Map.Entry<String, ConfigValue> t = iter.next();
            System.out.println(t.getKey() + "->" + t.getValue());
        }
        System.out.println("----------------------------------------");
        String userConfPath = Thread.currentThread().getContextClassLoader().getResource("user.conf").getPath();
        Config userConf = ConfigFactory.parseFile(new File(userConfPath));
        System.out.println(userConf.getClass());
        System.out.println(userConf.getObjectList("user"));
        System.out.println(userConf.getConfigList("user"));
        System.out.println("----------------------------------------");
        userConf = ConfigFactory.parseResources("user.conf");
        System.out.println(userConf.getClass());
        System.out.println(userConf.getObjectList("user"));
        System.out.println(userConf.getConfigList("user"));
        System.out.println("----------------------------------------");
        List<? extends Config> lc = userConf.getConfigList("user");
        for (Config c : lc) {
            System.out.println("name=" + c.getString("name") + ",age=" + c.getInt("age"));
        }
        System.out.println("----------------------------------------");
        Config mysqlConf = dbConf.withOnlyPath("mysql");
        mc = mysqlConf.root().unwrapped();
        ks = mc.keySet();
        for (String k : ks) {
            System.out.println(k + "=" + mc.get(k));
        }
        System.out.println("----------------------------------------");
        Config mergeConf = userConf.withFallback(mysqlConf);
        mc = mergeConf.root().unwrapped();
        ks = mc.keySet();
        for (String k : ks) {
            System.out.println(k + "=" + mc.get(k));
        }
        System.out.println("mergeConf.checkValid(mysqlConf)");
        mergeConf.checkValid(mysqlConf);
        System.out.println("mysqlConf.checkValid(mergeConf)");
        mysqlConf.checkValid(mergeConf);
        System.out.println("ConfigDemoII");
    }

}
