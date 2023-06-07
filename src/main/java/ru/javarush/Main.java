package ru.javarush;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import org.hibernate.SessionFactory;
import ru.javarush.evseychenko.dao.CityDAO;
import ru.javarush.evseychenko.dao.CountryDAO;
import ru.javarush.evseychenko.entity.City;
import ru.javarush.evseychenko.liquibase.Validator;
import ru.javarush.evseychenko.redis.CityCountry;
import ru.javarush.evseychenko.redis.Client;
import ru.javarush.evseychenko.sql.SqlClient;

import java.util.List;

import static java.util.Objects.nonNull;

public class Main {

    private final SessionFactory sessionFactory;
    private final RedisClient redisClient;

    private final ObjectMapper mapper;

    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CityCountry cityCountry;
    private final Client client;
    private final SqlClient sqlClient;

    public Main() {
        client = new Client();
        sqlClient = new SqlClient();
        sessionFactory = sqlClient.prepareRelationalDb();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);

        redisClient = client.prepareRedisClient();
        mapper = new ObjectMapper();

        cityCountry = new CityCountry();
    }


    public static void main(String[] args) {
        Validator validator = new Validator();
        validator.start();

        Main main = new Main();
        List<City> allCities = main.sqlClient.fetchData(main.sessionFactory, main.cityDAO);
        List<CityCountry> preparedData = main.cityCountry.transformData(allCities);
        main.client.pushToRedis(preparedData, main.redisClient, main.mapper);

        //закроем текущую сессию, чтоб точно делать запрос к БД, а не вытянуть данные из кэша
        main.sessionFactory.getCurrentSession().close();

        //выбираем случайных 10 id городов
        //так как мы не делали обработку невалидных ситуаций, используй существующие в БД id
        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        main.client.testRedisData(ids, main.redisClient, main.mapper);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        main.sqlClient.testMysqlData(ids, main.sessionFactory, main.cityDAO);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        main.shutdown();
    }

    private void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }



}