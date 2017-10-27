package com.tsutsuku.artcollection.utils;

import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 项目名： FastEC
 * 包名：   com.flj.latte.ec.main.DomPaser
 * 文件名:  XmlPaser
 * 作者：qxf on 2017/10/25 17:29
 * 邮箱：lorderike@gmail.com
 * 描述：TODO
 */

public class XmlPaser {
    public void  domParse() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("ResultPlist.xml");
        NodeList nodeList = document.getElementsByTagName("string");
        int length = nodeList.getLength();
        for(int i=0;i<length;i++){
            Node node = nodeList.item(i);
            String content = node.getTextContent();
            System.out.println(content);
        }

    }
    public void doPaserd(TextView textView, InputStream inputStream) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //获取xml文档结构
            Document document = documentBuilder.parse(inputStream);
            //获取节点
            NodeList nodeList = document.getElementsByTagName("string");
            int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                Node node = nodeList.item(i);
                String content = node.getTextContent();
                textView.setText(content);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

