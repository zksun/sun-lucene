package com.sun.lucene.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by zksun on 8/2/16.
 */
public class IndexCURD {

    private static String[] ids = {"1", "2", "3"};
    private static String[] names = {"kl", "wn", "sb"};
    private static String[] describes = {"shi yi ge mei nan zi", "Don't know", "Is an idiot\n"};

    private static String indexDir = System.getProperty("user.dir") + File.separator + "lucene";

    public static IndexWriter getIndexWriter(String indexDir) throws IOException {
        IndexWriterConfig writerConfig = new IndexWriterConfig(getAnalyzer());
        IndexWriter indexWriter = new IndexWriter(getDirectory(indexDir), writerConfig);
        Document document = new Document();
        for (int i = 0; i < ids.length; i++) {
            document.add(new StringField("ids", ids[i], Field.Store.YES));
            document.add(new StringField("names", names[i], Field.Store.YES));
            document.add(new TextField("describes", describes[i], Field.Store.YES));
            indexWriter.addDocument(document);
        }
        return indexWriter;
    }

    private static Analyzer getAnalyzer() {
        return new StandardAnalyzer();
    }

    private static Directory getDirectory(String indexDir) {
        try {
            return FSDirectory.open(Paths.get(indexDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static IndexReader getIndexReader() throws IOException {
        DirectoryReader open = DirectoryReader.open(getDirectory(indexDir));
        System.out.println("当前存储的文档数: " + open.numDocs());
        System.out.println("当前存储的文档数,包涵回收站的文档: " + open.maxDoc());
        System.out.println("回收站的文档数: " + open.numDeletedDocs());
        return open;
    }

    public void testInsert() throws IOException {
        getIndexWriter(indexDir).close();
        getIndexReader();
    }


    public static void main(String[] args) {
        System.out.println(indexDir);
    }
}
