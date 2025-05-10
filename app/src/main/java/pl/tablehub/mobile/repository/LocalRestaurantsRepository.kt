package pl.tablehub.mobile.repository

import pl.tablehub.mobile.model.Restaurant

class LocalRestaurantsRepository {
    private val _restaurants: MutableList<Restaurant> = mutableListOf()
    val restaurants : MutableList<Restaurant> get() = _restaurants
    fun addRestaurants(restaurants: List<Restaurant>) {
        _restaurants.addAll(restaurants)
    }
    fun updateRestaurant(id: Long, restaurant: Restaurant) {
        val index = _restaurants.indexOfFirst { it.id == id }
        if (index != -1) {
            _restaurants[index] = restaurant
        }
    }
    fun removeRestaurants(ids: List<Long>) {
        _restaurants.removeAll { it.id in ids }
    }
}