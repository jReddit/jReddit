package com.github.jreddit.request.retrieval.param;

/**
 * Enum to represent the possible query syntaxes.
 * @author Simon Kassing
 */
public enum QuerySyntax {

    CLOUDSEARCH            ("cloudsearch"), 
    LUCENE                ("lucene"), 
    PLAIN                ("plain");

    private final String value;

    QuerySyntax(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
