package com.omrlnr.jreddit;

/**
 *
 * This class represents a reddit fullname
 *
 */
public class Fullname {

    /**
     *  Fullname types
     */
    public static final String TYPE_COMMENT         = "t1";
    public static final String TYPE_ACCOUNT         = "t2";
    public static final String TYPE_LINK            = "t3";
    public static final String TYPE_MESSAGE         = "t4";
    public static final String TYPE_SUBREDDIT       = "t5";
    public static final String TYPE_AWARD           = "t6";
    public static final String TYPE_PROMOCAMPAIGN   = "t8";

    private String _type;
    private String _id;

    public Fullname(String type, String id) {
        _type   = type;
        _id     = id;
    }

    public String getType() { return _type; }
    public String getId() { return _id; }

    public boolean equals(Fullname fullname) {
        return  _type.equals(fullname.getType()) &&
                _id.equals(fullname.getId());
    }

    public String toString() {
        return  _type + "_" + _id;
    }

}
