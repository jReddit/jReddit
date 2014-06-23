package com.github.jreddit.utils;

import java.util.List;

public class PaginationResult<T>
{
    private String after;
    private List<T> results;

    public String getAfter()
    {
        return after;
    }

    public void setAfter(String after)
    {
        this.after = after;
    }

    public List<T> getResults()
    {
        return results;
    }

    public void setResults(List<T> submissions)
    {
        this.results = submissions;
    }
}
