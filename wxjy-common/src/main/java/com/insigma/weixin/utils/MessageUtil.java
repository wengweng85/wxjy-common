package com.insigma.weixin.utils;

import com.insigma.weixin.pojo.ImageMessage;
import com.insigma.weixin.pojo.News;
import com.insigma.weixin.pojo.NewsMessage;
import com.insigma.weixin.pojo.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信消息工具类
 * @author admin
 *
 */
public class MessageUtil {

    /**
     * xml 转 map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
        Map<String, String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for(Element e : list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        System.out.println("weixin send data:"+map.toString());
        return map;
    }
    
    /**
     * 将文本消息对象转换成xml
     * @param textMessage
     * @return
     * 注意事项：添加xstream.jar
     */
    public static String textMessageToXml(TextMessage textMessage){
        //XStream xStream = new XStream(new StaxDriver());
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }
    
    /**
     * 图文消息转换为xml
     * @param newsMessage
     * @return
     */
      public static String newsMessageToXml(NewsMessage newsMessage){
        //XStream xStream = new XStream(new StaxDriver());
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);
      }
      
      /**
       * 将图片消息转换为xml
       * @param imageMessage
       * @return
       */
      public static String imageMessageToXml(ImageMessage imageMessage){
          //XStream xStream = new XStream(new StaxDriver());
          xStream.alias("xml", imageMessage.getClass());
          return xStream.toXML(imageMessage);
      }
    
    /**
     * xStream本身不支持生成cdata块生成，对xstream扩展，让其自动生成cdata块
     */
    private static XStream xStream = new XStream(new StaxDriver(){
        public HierarchicalStreamWriter createWriter(Writer out){
            return new PrettyPrintWriter(out){
                boolean cdata = true;
                
                public void startNode(String name,Class clazz){
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer,String text){
                    if(cdata){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else{
                        writer.write(text);
                    }
                }
            };
        }
    });
        
}