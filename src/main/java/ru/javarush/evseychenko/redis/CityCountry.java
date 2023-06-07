package ru.javarush.evseychenko.redis;

import ru.javarush.evseychenko.entity.City;
import ru.javarush.evseychenko.entity.Continent;
import ru.javarush.evseychenko.entity.Country;
import ru.javarush.evseychenko.entity.CountryLanguage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CityCountry {
    private Integer id;

    private String name;

    private String district;

    private Integer population;

    private String countryCode;

    private String alternativeCountryCode;

    private String countryName;

    private Continent continent;

    private String countryRegion;

    private BigDecimal countrySurfaceArea;

    private Integer countryPopulation;

    private Set<Language> languages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setDistrict(String district) {
        this.district = district;
    }


    public void setPopulation(Integer population) {
        this.population = population;
    }


    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    public void setAlternativeCountryCode(String alternativeCountryCode) {
        this.alternativeCountryCode = alternativeCountryCode;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public void setContinent(Continent continent) {
        this.continent = continent;
    }


    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }


    public void setCountrySurfaceArea(BigDecimal countrySurfaceArea) {
        this.countrySurfaceArea = countrySurfaceArea;
    }


    public void setCountryPopulation(Integer countryPopulation) {
        this.countryPopulation = countryPopulation;
    }


    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getAlternativeCode());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());
            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setOfficial(cl.getOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }
}
