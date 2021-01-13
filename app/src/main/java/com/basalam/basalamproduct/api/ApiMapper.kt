package com.basalam.basalamproduct.api

import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.basalamproduct.model.Photo
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.model.Rating
import com.basalam.basalamproduct.model.Vendor
import com.basalam.basalamproduct.util.EntityMapper
import javax.inject.Inject

class ApiMapper @Inject constructor() : EntityMapper<GetProductsQuery.Product, Product> {
    override fun mapFromEntity(entity: GetProductsQuery.Product): Product {
        val vendorP = Vendor(name = entity.vendor?.name!!)
        val ratingP = Rating(rating = entity.rating?.rating!!, count = entity.rating.count!!)
        var photoP = Photo("https://basalam.com/img/basalam-logotype-square.png")
        if (entity.photo?.url != null) {
            photoP = Photo(url = entity.photo.url)
        }
        return Product(
            id = entity.id!!,
            vendor = vendorP,
            name = entity.name,
            weight = entity.weight!!,
            price = entity.price,
            rating = ratingP,
            photo = photoP
        )
    }

    fun mapFromEntities(entities: List<GetProductsQuery.Product>): List<Product> {
        return entities.map { mapFromEntity(it) }
    }
}