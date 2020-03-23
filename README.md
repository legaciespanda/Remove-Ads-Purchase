# Remove-Ads-Purchase

# How in-app Billing actually works:

When making an in-app purchase, you actually buy a product from google. Consider it as a marketplace similar to Amazon where a seller puts his products for sale and users buy it from there. Similarly a developer needs to add a product in his developer console of his particular app. For example “Remove Ads” is a product which is added in the Google Play developer console for a particular app. When users pay for removing ads for a game/app, they actually buy the product from Developer Console of the developer.

Lets talk about the type of products. There are mainly two types for products in Android in app Billing.

MANAGED PRODUCT – Its a type of product which a buyer pays for once. In other words, buyer owns the product for lifetime after making the purchase, and he cannot buy that same product ever again. If developer has given an interface to buy the same product again, it will throw an error. But these products can also be set as consumed for a particular buyer. And a consumed product can be bought again!
Example of Consumable Managed Product:
For example when a user buys a pack of 600 gems in a game, he buys a product named “600 Gems“. But as soon as the user buys 600 gems, this product is set as consumed so that it can be purchased again.

Example of Simple Managed Product:
This is a simple purchase of Managed Product. For example a user buys “Remove Ads” product which has to be owned for life time. So it cannot be purchased again by the same user. Developer do not write any code for consuming the product after purchase.

SUBSCRIPTION – The name itself says it all. It a subscription of any service. For example a purchase for unlimited fuel for one month in car racing game (Example of the Sample In App Billing App provided by Google).
