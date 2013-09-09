package com.msi.manning.restaurant.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * SAX Handler impl for Google Base API and restaurant Review bean.
 * 
 * @author charliecollins
 */
public class ReviewHandler extends DefaultHandler {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ENTRY = "entry";
    private static final String R_AUTHOR = "review_author";
    private static final String R_CONTENT = "content";
    private static final String R_DATE = "review_date";
    private static final String R_IMAGE_LINK = "image_link";
    private static final String R_LINK = "link";
    private static final String R_LOCATION = "location";
    private static final String R_NAME = "name_of_item_reviewed";
    private static final String R_PHONE = "phone_of_item_reviewed";
    private static final String R_RATING = "rating";

    private boolean authorChars;
    private boolean contentChars;
    private boolean dateChars;
    private boolean imageLinkChars;
    private boolean locationChars;
    private boolean nameChars;
    private int numEntries;
    private boolean phoneChars;
    private boolean ratingChars;
    private Review review;
    private final ArrayList<Review> reviews;
    private boolean startEntry;

    public ReviewHandler() {
        this.reviews = new ArrayList<Review>();
    }

    @Override
    public void startDocument() throws SAXException {
    }
  
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

        if (localName.equals(ReviewHandler.ENTRY)) {
            this.startEntry = true;
            this.review = new Review();
        }

        if (this.startEntry) {
            if (localName.equals(ReviewHandler.R_NAME)) {
                this.nameChars = true;
            } else if (localName.equals(ReviewHandler.R_AUTHOR)) {
                this.authorChars = true;
            } else if (localName.equals(ReviewHandler.R_LINK)) {
                String rel = getAttributeValue("rel", atts);
                if (rel != null && rel.equals("alternate")) {
                    this.review.link = getAttributeValue("href", atts);
                }
            } else if (localName.equals(ReviewHandler.R_LOCATION)) {
                this.locationChars = true;
            } else if (localName.equals(ReviewHandler.R_RATING)) {
                this.ratingChars = true;
            } else if (localName.equals(ReviewHandler.R_PHONE)) {
                this.phoneChars = true;
            } else if (localName.equals(ReviewHandler.R_DATE)) {
                this.dateChars = true;
            } else if (localName.equals(ReviewHandler.R_CONTENT)) {
                this.contentChars = true;
            } else if (localName.equals(ReviewHandler.R_IMAGE_LINK)) {
                this.imageLinkChars = true;
            }
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (localName.equals(ReviewHandler.ENTRY)) {
            this.startEntry = false;
            this.numEntries++;
            this.reviews.add(this.review);
        }

        if (this.startEntry) {
            if (localName.equals(ReviewHandler.R_NAME)) {
                this.nameChars = false;
            } else if (localName.equals(ReviewHandler.R_AUTHOR)) {
                this.authorChars = false;
            } else if (localName.equals(ReviewHandler.R_LOCATION)) {
                this.locationChars = false;
            } else if (localName.equals(ReviewHandler.R_RATING)) {
                this.ratingChars = false;
            } else if (localName.equals(ReviewHandler.R_PHONE)) {
                this.phoneChars = false;
            } else if (localName.equals(ReviewHandler.R_DATE)) {
                this.dateChars = false;
            } else if (localName.equals(ReviewHandler.R_CONTENT)) {
                this.contentChars = false;
            } else if (localName.equals(ReviewHandler.R_IMAGE_LINK)) {
                this.imageLinkChars = false;
            }
        }
    }   
    
    @Override
    public void characters(char[] ch, int start, int length) {
        String chString = "";
        if (ch != null) {
            chString = new String(ch, start, length);
        }

        if (this.startEntry) {
            if (this.nameChars) {
                this.review.name = chString;
            } else if (this.authorChars) {
                this.review.author = chString;
            } else if (this.locationChars) {
                this.review.location = chString;
            } else if (this.ratingChars) {
                this.review.rating = chString;
            } else if (this.phoneChars) {
                this.review.phone = chString;
            } else if (this.dateChars) {
                if (ch != null) {
                    try {
                        this.review.date = ReviewHandler.DATE_FORMAT.parse(chString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else if (this.contentChars) {
                this.review.content = new String(chString);
            } else if (this.imageLinkChars) {
                this.review.imageLink = new String(chString);
            }
        }
    }
    
    public ArrayList<Review> getReviews() {
        return this.reviews;
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
}
