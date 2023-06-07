package ru.javarush.evseychenko.redis;

import java.math.BigDecimal;

public class Language {
    private String language;
    private Boolean isOfficial;
    private BigDecimal percentage;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOfficial(Boolean official) {
        isOfficial = official;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

}
