package com.msi.manning.network.data.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class DeliciousHandler extends DefaultHandler {

    private static final String POST = "post";

    private List<DeliciousPost> posts;

    public DeliciousHandler() {
        this.posts = new ArrayList<DeliciousPost>();
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals(DeliciousHandler.POST)) {
            DeliciousPost post = new DeliciousPost();
            post.setDesc(getAttributeValue("description", atts));
            post.setHref(getAttributeValue("href", atts));
            post.setTag(getAttributeValue("tag", atts));
            this.posts.add(post);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    }

    @Override
    public void characters(char ch[], int start, int length) {
    }

    private String getAttributeValue(String attName, Attributes atts) {
        String result = null;
        for (int i = 0; i < atts.getLength(); i++) {
            String thisAtt = atts.getLocalName(i);
            if (attName.equals(thisAtt)) {
                result = atts.getValue(i);
                break;
            }
        }
        return result;
    }

    public List<DeliciousPost> getPosts() {
        return this.posts;
    }
}
