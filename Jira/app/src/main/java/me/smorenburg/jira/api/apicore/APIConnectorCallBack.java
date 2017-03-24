package me.smorenburg.jira.api.apicore;

public interface APIConnectorCallBack<T> {
    void resultCallBack(T result);
}
