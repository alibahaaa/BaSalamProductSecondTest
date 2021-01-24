package com.basalam.data.mapper

import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.data.entities.Photo
import com.basalam.data.entities.Product
import com.basalam.data.entities.Rating
import com.basalam.data.entities.Vendor
import com.basalam.domain.entities.PhotoEntity
import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.entities.RatingEntity
import com.basalam.domain.entities.VendorEntity
import javax.inject.Inject

class ToEntityMapper @Inject constructor (): EntityMapper<ProductEntity, Product> {
    override fun mapFromEntity(entity: ProductEntity): Product {
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

    fun mapFromEntities(entities: List<ProductEntity>): List<Product> {
        return entities.map { mapFromEntity(it) }
    }
}