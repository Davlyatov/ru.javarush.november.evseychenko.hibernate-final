package ru.javarush.evseychenko.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "world", name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String district;

    private Integer population;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public String getDistrict() {
        return district;
    }

    public Integer getPopulation() {
        return population;
    }

}
