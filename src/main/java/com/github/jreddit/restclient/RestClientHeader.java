package com.github.jreddit.restclient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.http.message.BasicHeader;

public class RestClientHeader extends BasicHeader {
    /**
     * Constructor with name and value
     *
     * @param name  the header name
     * @param value the header value
     */
    public RestClientHeader(String name, String value) {
        super(name, value);
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder()
                .append(getName())
                .append(getValue())
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj instanceof RestClientHeader){
            final RestClientHeader other = (RestClientHeader) obj;
            return new EqualsBuilder()
                    .append(getName(), other.getName())
                    .append(getValue(), other.getValue())
                    .isEquals();
        } else{
            return false;
        }
    }
}
