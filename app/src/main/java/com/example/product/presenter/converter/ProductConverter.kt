import com.example.product.presenter.converter.ImageConverter
import com.example.product.presenter.converter.VariantConverter
import com.example.product.model.*
import com.example.product.ui.model.Product

object ProductConverter {


    fun productDTOToProduct(dto: ProductDTO): Product {
        val product = Product()
        product.id=dto.id
        product.name = dto.name
        product.category = dto.category
        product.brand = dto.brand
        product.description = dto.description
        product.status = dto.status
        product.variants.addAll( dto.variants.let { VariantConverter.listVariantDTOtoListVariant(it) } )
        product.images.addAll(dto.images?.let { ImageConverter.listImageToListImageDTO(it) } ?: mutableListOf())
        return product
    }

    fun listProductDTOToListProduct(initial: List<ProductDTO>): List<Product> {
        return initial.map { productDTOToProduct(it) }
    }
}
