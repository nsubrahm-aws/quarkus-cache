package org.acme.cache;

import java.time.LocalDate;

import org.springframework.cache.annotation.CachePut;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CacheResult;

@ApplicationScoped
public class WeatherForecastService {
  @Inject
  @CacheName("weather-cachce")
  Cache weatherCache;

  @CacheResult(cacheName = "weather-cache")
  public String getDailyForecast(LocalDate date, String city) {
    try {
      Thread.sleep(2000L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return date.getDayOfWeek() + " will be " + getDailyResult(date.getDayOfMonth() % 4) + " in " + city;
  }

  @CacheInvalidate(cacheName = "weather-cache")
  public void deleteCacheItem(@CacheKey LocalDate localDate, @CacheKey String city) {

  }

  @CachePut(cacheNames = "weather-cache")
  public String updateCacheItem(LocalDate localDate, String city) {
    return "windy";
  }

  private String getDailyResult(int dayOfMonthModuloFour) {
    switch (dayOfMonthModuloFour) {
      case 0:
        return "sunny";
      case 1:
        return "cloudy";
      case 2:
        return "chilly";
      case 3:
        return "rainy";
      default:
        throw new IllegalArgumentException();
    }
  }
}