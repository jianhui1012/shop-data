package com.example;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.XHTMLValidationType;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Desc:
 * Company: lifang
 * Copyright: Copyright (c) 2023
 *
 * @author djh
 * @since 2023/04/23 16/54
 */
public class TestHtmlToWord {

    public static void main(String[] args) throws IOException {
        String path = TestHtmlToWord.class.getClassLoader().getResource("").getPath();
        //加载html文件
        Document doc = new Document();
        doc.loadFromFile("/Users/golike/Documents/Hope/ShareShop/shop-data/src/main/resources/static/12.html", FileFormat.Html, XHTMLValidationType.None);
        //保存为Word格式到指定路径
        doc.saveToFile("htmltoWord.docx", FileFormat.Docx_2013);
    }
}
