package pl.tablehub.mobile.fragments.account.gamification.rewards.composables

import pl.tablehub.mobile.model.v2.Address
import pl.tablehub.mobile.model.v2.Image
import pl.tablehub.mobile.model.v2.Reward

val rewardList = listOf(
    Reward(
        title = "Free Dessert with Main Course",
        additionalDescription = "Valid on weekdays only. Must present coupon.",
        restaurantName = "The Italian Corner",
        restaurantAddress = Address(
            streetNumber = 145,
            apartmentNumber = null,
            street = "Oak St",
            city = "New York",
            postalCode = "10001",
            country = "USA"
        ),
        id = 1,
        image = Image(
            url = "https://fastly.picsum.photos/id/323/200/200.jpg?hmac=EoedzCHJZRv1-7_RBKDcba4cXIfclsicfsYbW3-VEsA",
            altText = "Free dessert image",
            ratio = 1.0
        ),
        redeemed = false
    ),
    Reward(
        title = "20% Off Your Entire Bill",
        additionalDescription = "Excludes alcohol. Maximum discount of $50.",
        restaurantName = "Sushi Heaven",
        restaurantAddress = Address(
            streetNumber = 7,
            apartmentNumber = 201,
            street = "Elm Street",
            city = "Los Angeles",
            postalCode = "90012",
            country = "USA"
        ),
        id = 2,
        image = Image(
            url = "https://fastly.picsum.photos/id/660/200/200.jpg?hmac=5UOdBCKDcPq_zS0RAVkvSD934EYVyCEdExCagJur-g8",
            altText = "20% discount image",
            ratio = 1.0
        ),
        redeemed = false
    ),
    Reward(
        title = "Buy One Coffee, Get One Free",
        additionalDescription = null,
        restaurantName = "The Cozy Cafe",
        restaurantAddress = Address(
            streetNumber = 32,
            apartmentNumber = null,
            street = "Main Street",
            city = "London",
            postalCode = "SW1A 0AA",
            country = "UK"
        ),
        id = 3,
        image = Image(
            url = "https://fastly.picsum.photos/id/522/200/200.jpg?hmac=-4K81k9CA5C9S2DWiH5kP8rMvaAPk2LByYZHP9ejTjA",
            altText = "Coffee offer image",
            ratio = 1.0
        ),
        redeemed = false
    ),
    Reward(
        title = "Complimentary Appetizer",
        additionalDescription = "Choose any appetizer up to $15 value.",
        restaurantName = "Spice Route Grill",
        restaurantAddress = Address(
            streetNumber = 987,
            apartmentNumber = 12,
            street = "Maple Ave",
            city = "Toronto",
            postalCode = "M5V 2J5",
            country = "Canada"
        ),
        id = 4,
        image = Image(
            url = "https://fastly.picsum.photos/id/281/200/200.jpg?hmac=5FvZ-Y5zbbpS3-mJ_mp6-eH61MkwhUJi9qnhscegqkY",
            altText = "Appetizer image",
            ratio = 1.0
        ),
        redeemed = false
    ),
    Reward(
        title = "Loyalty Point Bonus",
        additionalDescription = "Earn 50 bonus loyalty points on your next visit.",
        restaurantName = "Burger Joint Deluxe",
        restaurantAddress = Address(
            streetNumber = 55,
            apartmentNumber = null,
            street = "Broadway",
            city = "Sydney",
            postalCode = "2000",
            country = "Australia"
        ),
        id = 5,
        image = Image(
            url = "https://fastly.picsum.photos/id/376/200/200.jpg?hmac=lM2SnAPO9nDnPBP5FjJOFIJSaRoPKUJRovk6goT_nA4",
            altText = "Loyalty bonus image",
            ratio = 1.0
        ),
        redeemed = false
    )
)
