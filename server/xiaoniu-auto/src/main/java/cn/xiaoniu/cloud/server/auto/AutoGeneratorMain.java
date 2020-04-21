package cn.xiaoniu.cloud.server.auto;

import java.io.IOException;
import java.util.Properties;

import java.io.InputStream;

public class AutoGeneratorMain {
    Properties properties = null;

    public AutoGeneratorMain(String confName) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(confName);
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
