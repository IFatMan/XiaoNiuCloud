package cn.xiaoniu.cloud.server.auto.engine;//package com.baomidou.mybatisplus.generator.engine;
//
//import com.baomidou.mybatisplus.generator.config.ConstVal;
//import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.util.Map;
//
//
//public class FreemarkerTemplateEngine
//        extends AbstractTemplateEngine {
//    private Configuration configuration;
//
//    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
//        super.init(configBuilder);
//        this.configuration = new Configuration();
//        this.configuration.setDefaultEncoding(ConstVal.UTF8);
//        this.configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/");
//        return this;
//    }
//
//
//    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
//        Template template = this.configuration.getTemplate(templatePath);
//        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile));
//        template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
//        fileOutputStream.close();
//        logger.debug("����:" + templatePath + ";  ����:" + outputFile);
//    }
//
//
//    public String templateFilePath(String filePath) {
//        StringBuilder fp = new StringBuilder();
//        fp.append(filePath).append(".ftl");
//        return fp.toString();
//    }
//}
