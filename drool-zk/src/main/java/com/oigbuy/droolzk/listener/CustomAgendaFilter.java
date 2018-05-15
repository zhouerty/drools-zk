package com.oigbuy.droolzk.listener;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.Set;

/**
 * @author qiang.zhou
 * on 2018-05-14.
 */
public class CustomAgendaFilter implements AgendaFilter {
    /**
     * 传入的rule name
     */
    private final Set<String> ruleNamesThatAreAllowedToFire;

    public CustomAgendaFilter(Set<String> ruleNamesThatAreAllowedToFire) {
        this.ruleNamesThatAreAllowedToFire = ruleNamesThatAreAllowedToFire;
    }

    @Override
    public boolean accept(Match match) {
        return ruleNamesThatAreAllowedToFire.contains(match.getRule().getName());
    }
}
