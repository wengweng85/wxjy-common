package com.insigma.freemarker.form;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * freemarker自定义标签测试
 *
 */
public class RepeatDirective implements TemplateDirectiveModel  {

    /**
     * @param environment
     * @param map
     *            <@repeat count=3;
     *            cnt>其中count=3就以键值对的形式存在map中，只是3不是java类型，而是TemplateModel类型
     *            (整数:TemplateNumberModel
     *            ,字符串:TemplateScalarModel,boolean:TemplateBooleanModel 等)，
     *            所以利用map.getValue(......)返回的是一个TemplateModel类型的数据，需转换成int类型数据。
     * @param templatemodel
     *            变量，如cnt，atemplatemodel是一个TemplateModel类型的数组，我们可以在程序中给它赋值
     * @param templatedirectivebody
     */
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templatemodel, TemplateDirectiveBody templatedirectivebody) throws TemplateException, IOException {
        Writer out=null;
        try{
            if(templatedirectivebody==null){
                throw new TemplateModelException("null body");
            }else{
                out = environment.getOut();
                TemplateNumberModel numberModel = (TemplateNumberModel) map.get("count");
                int count = numberModel.getAsNumber().intValue();
                for (int i = 0; i < count; i++) {
                    if (templatemodel.length > 0) {
                        templatemodel[0] = new SimpleNumber(i + 1);
                    }
                    templatedirectivebody.render(out);
                }
            }
        }finally {
            if(out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
