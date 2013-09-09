/*
 * JobEntry.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import android.os.Bundle;

public class JobEntry {

    private String _jobid = "";
    private String _status = "";
    private String _customer = "";
    private String _identifier = "";
    private String _address = "";
    private String _city = "";
    private String _state = "";
    private String _zip = "";
    private String _product = "";
    private String _producturl = "";
    private String _comments = "";

    JobEntry() {
        // constructor

    }

    public String get_jobid() {
        return this._jobid;
    }

    public void set_jobid(String jobid) {
        this._jobid = jobid;
    }

    public String get_customer() {
        return this._customer;
    }

    public void set_customer(String _customer) {
        this._customer = _customer;
    }

    public String get_status() {
        return this._status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_identifier() {
        return this._identifier;
    }

    public void set_identifier(String _identifier) {
        this._identifier = _identifier;
    }

    public String get_address() {
        return this._address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_city() {
        return this._city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_state() {
        return this._state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public String get_zip() {
        return this._zip;
    }

    public void set_zip(String _zip) {
        this._zip = _zip;
    }

    public String get_product() {
        return this._product;
    }

    public void set_product(String _product) {
        this._product = _product;
    }

    public String get_producturl() {
        return this._producturl;
    }

    public void set_producturl(String _producturl) {
        this._producturl = _producturl;
    }

    public String get_comments() {
        return this._comments;
    }

    public void set_comments(String _comments) {
        this._comments = _comments;
    }

    @Override
    public String toString() {
        return this._jobid + ": " + this._customer + ": " + this._product;
    }

    public String toXMLString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("<job>");
        sb.append("<id>" + this._jobid + "</id>");
        sb.append("<status>" + this._status + "</status>");
        sb.append("<customer>" + this._customer + "</customer>");
        sb.append("<address>" + this._address + "</address>");
        sb.append("<city>" + this._city + "</city>");
        sb.append("<state>" + this._state + "</state>");
        sb.append("<zip>" + this._zip + "</zip>");
        sb.append("<product>" + this._product + "</product>");
        sb.append("<producturl>" + this._producturl + "</producturl>");
        sb.append("<comments>" + this._comments + "</comments>");
        sb.append("</job>");
        return sb.toString() + "\n";
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString("jobid", this._jobid);
        b.putString("status", this._status);
        b.putString("customer", this._customer);
        b.putString("address", this._address);
        b.putString("city", this._city);
        b.putString("state", this._state);
        b.putString("zip", this._zip);
        b.putString("product", this._product);
        b.putString("producturl", this._producturl);
        b.putString("comments", this._comments);

        return b;
    }

    public static JobEntry fromBundle(Bundle b) {
        JobEntry je = new JobEntry();
        je.set_jobid(b.getString("jobid"));
        je.set_status(b.getString("status"));
        je.set_customer(b.getString("customer"));
        je.set_address(b.getString("address"));
        je.set_city(b.getString("city"));
        je.set_state(b.getString("state"));
        je.set_zip(b.getString("zip"));
        je.set_product(b.getString("product"));
        je.set_producturl(b.getString("producturl"));
        je.set_comments(b.getString("comments"));
        return je;
    }
}
