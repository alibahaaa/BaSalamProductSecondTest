package com.basalam.data.mapper

import com.basalam.basalamproduct.GetProductsQuery
import com.basalam.domain.entities.PhotoEntity
import com.basalam.domain.entities.ProductEntity
import com.basalam.domain.entities.RatingEntity
import com.basalam.domain.entities.VendorEntity
import javax.inject.Inject

class ApiMapper @Inject constructor(): EntityMapper<GetProductsQuery.Product, ProductEntity> {
    override fun mapFromEntity(entity: GetProductsQuery.Product): ProductEntity {
        val vendorP = VendorEntity(name = entity.vendor?.name!!)
        val ratingP = RatingEntity(rating = entity.rating?.rating!!, count = entity.rating.count!!)
        var photoP = PhotoEntity("https://basalam.com/img/basalam-logotype-square.png")
        if (entity.photo?.url != null) {
            photoP = PhotoEntity(url = entity.photo.url)
        }
        return ProductEntity(
            id = entity.id!!,
            vendor = vendorP,
            name = entity.name,
            weight = entity.weight!!,
            price = entity.price,
            rating = ratingP,
            photo = photoP
        )
    }
    fun mapFromEntities(entities: List<GetProductsQuery.Product>): List<ProductEntity> {
        return entities.map { mapFromEntity(it) }
    }
}