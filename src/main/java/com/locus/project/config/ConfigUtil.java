package com.locus.project.config;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author priyanka_s [priyanka.singh@zoomcar.com]
 *         Part of coordinates-simulation
 *         on 27/10/17.
 */
public class ConfigUtil {
    private static ServiceConfig serviceConfig;

    public static ServiceConfig getServiceConfig() {

        if (serviceConfig == null) {
            InputStream input = null;

            try {
                input = new FileInputStream(new File(
                        "src/main/java/com/locus/project/config.yml"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Yaml yaml = new Yaml();
            serviceConfig = yaml.loadAs(input, ServiceConfig.class);
        }

        return serviceConfig;
    }
}
